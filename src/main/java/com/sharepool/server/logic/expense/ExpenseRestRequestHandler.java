package com.sharepool.server.logic.expense;

import com.sharepool.server.dal.ExpenseRepository;
import com.sharepool.server.dal.TourRepository;
import com.sharepool.server.dal.UserRepository;
import com.sharepool.server.domain.Expense;
import com.sharepool.server.domain.Tour;
import com.sharepool.server.domain.User;
import com.sharepool.server.rest.expense.dto.*;
import com.sharepool.server.rest.user.dto.UserDto;
import com.sharepool.server.rest.util.RestHelperUtil;
import com.sharepool.server.rest.util.auth.UserContext;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ExpenseRestRequestHandler {

    private final Logger logger;

    private final TourRepository tourRepository;
    private final UserRepository userRepository;
    private final ExpenseRepository expenseRepository;

    private final ExpenseMapper expenseMapper;

    public ExpenseRestRequestHandler(
            Logger logger,
            TourRepository tourRepository,
            ExpenseMapper expenseMapper,
            UserRepository userRepository,
            ExpenseRepository expenseRepository
    ) {
        this.logger = logger;
        this.tourRepository = tourRepository;
        this.expenseMapper = expenseMapper;
        this.userRepository = userRepository;
        this.expenseRepository = expenseRepository;
    }

    public ExpenseRequestResponseDto requestExpense(Long tourId) {
        Tour tour = RestHelperUtil.checkExists(tourRepository, tourId, Tour.class);

        User owner = tour.getOwner();
        checkOwnerOfTourWasSet(tourId, owner);

        return expenseMapper.userAndTourToExpenseRequestResponseDto(owner, tour);
    }

    private void checkOwnerOfTourWasSet(Long tourId, User owner) {
        if (owner == null) {
            logger.error("No owner found for tour {}. " +
                    "Something went wrong while persisting as this should always be the case", tourId);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void confirmExpense(ExpenseConfirmationDto expenseConfirmationDto) {
        Tour tour = RestHelperUtil.checkExists(tourRepository, expenseConfirmationDto.getTourId(), Tour.class);

        User receiver = tour.getOwner();
        checkOwnerOfTourWasSet(expenseConfirmationDto.getTourId(), receiver);

        User payer = RestHelperUtil.checkExists(userRepository, expenseConfirmationDto.getPayerId(), User.class);

        Expense expense = new Expense(
                LocalDateTime.now(),
                tour.getCurrency(),
                tour.getTourCost(),
                payer,
                receiver,
                tour);

        expenseRepository.save(expense);
    }

    public Double getTotalBalance(UserContext userContext) {
        ExpensesWrapper allExpenses = getAllExpenses(userContext, null);

        double receivedSum = allExpenses.getReceivingExpenses().stream()
                .mapToDouble(ExpensePerUserDto::getSumOfExpenses)
                .sum();

        double payedSum = allExpenses.getPayedExpenses().stream()
                .mapToDouble(ExpensePerUserDto::getSumOfExpenses)
                .sum();

        return receivedSum - payedSum;
    }

    public ExpensesWrapper getAllExpenses(UserContext userContext, Long receiverId) {
        List<ExpensePerUserDto> receivingExpenses = new ArrayList<>();
        List<ExpensePerUserDto> payedExpenses = new ArrayList<>();

        User user = userContext.getUser();
        if (receiverId != null) {
            User receiver = RestHelperUtil.checkExists(userRepository, receiverId, User.class);

            expenseRepository.findAllByPayerAndReceiver(user, receiver)
                    .stream()
                    .map(expenseMapper::expenseToExpenseDto)
                    .forEach(e -> fillListsBasedOnLoggedInUser(receivingExpenses, payedExpenses, user, e));

            return new ExpensesWrapper(receivingExpenses, payedExpenses);
        }

        expenseRepository.findAllByPayerOrReceiver(user, user)
                .stream()
                .map(expenseMapper::expenseToExpenseDto)
                .forEach(e -> fillListsBasedOnLoggedInUser(receivingExpenses, payedExpenses, user, e));

        return new ExpensesWrapper(receivingExpenses, payedExpenses);
    }

    private void fillListsBasedOnLoggedInUser(
            List<ExpensePerUserDto> receivingExpenses,
            List<ExpensePerUserDto> payedExpenses,
            User user,
            ExpenseDto expense
    ) {
        if (expense.getPayer().getUserName().equals(user.getUserName())) {
            createOrAddToUser(payedExpenses, expense);
        } else if (expense.getReceiver().getUserName().equals(user.getUserName())) {
            createOrAddToUser(receivingExpenses, expense);
        }
    }

    private void createOrAddToUser(List<ExpensePerUserDto> expenses, ExpenseDto expense) {
        UserDto receiver = expense.getReceiver();
        double amount = expense.getAmount();

        filterDuplicateInformation(expense);

        for (ExpensePerUserDto expensePerUser : expenses) {
            if (expensePerUser.getReceiver().getUserName().equals(receiver.getUserName())) {
                expensePerUser.setSumOfExpenses(expensePerUser.getSumOfExpenses() + amount);

                filterDuplicateInformation(expense);

                expensePerUser.getExpenses().add(expense);
                return;
            }
        }

        List<ExpenseDto> expensesForUser = new ArrayList<>();
        expensesForUser.add(expense);
        expenses.add(new ExpensePerUserDto(
                receiver,
                amount,
                expensesForUser)
        );
    }

    private void filterDuplicateInformation(ExpenseDto expense) {
        expense.setPayer(null);
        expense.setReceiver(null);
    }
}
