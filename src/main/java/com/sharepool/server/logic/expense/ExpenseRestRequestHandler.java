package com.sharepool.server.logic.expense;

import com.sharepool.server.dal.ExpenseRepository;
import com.sharepool.server.dal.TourRepository;
import com.sharepool.server.dal.UserRepository;
import com.sharepool.server.domain.Expense;
import com.sharepool.server.domain.Tour;
import com.sharepool.server.domain.User;
import com.sharepool.server.rest.expense.dto.ExpenseConfirmationDto;
import com.sharepool.server.rest.expense.dto.ExpenseDto;
import com.sharepool.server.rest.expense.dto.ExpenseRequestResponseDto;
import com.sharepool.server.rest.expense.dto.ExpensesWrapper;
import com.sharepool.server.rest.util.RestHelperUtil;
import com.sharepool.server.rest.util.auth.UserContext;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
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
        Tour tour = RestHelperUtil.checkTourExists(tourRepository, tourId);

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
        Tour tour = RestHelperUtil.checkTourExists(tourRepository, expenseConfirmationDto.getTourId());

        User receiver = tour.getOwner();
        checkOwnerOfTourWasSet(expenseConfirmationDto.getTourId(), receiver);

        User payer = RestHelperUtil.checkUserExists(userRepository, expenseConfirmationDto.getPayerId());

        Expense expense = new Expense(
                LocalDate.now(),
                tour.getCurrency(),
                tour.getTourCost(),
                payer,
                receiver,
                tour);

        expenseRepository.save(expense);
    }

    public ExpensesWrapper getAllExpenses(UserContext userContext, Long receiverId) {
        List<ExpenseDto> receivingExpenses = new ArrayList<>();
        List<ExpenseDto> payedExpenses = new ArrayList<>();

        User user = userContext.getUser();
        if (receiverId != null) {
            User receiver = RestHelperUtil.checkUserExists(userRepository, receiverId);

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
            List<ExpenseDto> receivingExpenses,
            List<ExpenseDto> payedExpenses,
            User user,
            ExpenseDto expenseDto) {

        if (expenseDto.getPayer().getUserName().equals(user.getUserName())) {
            payedExpenses.add(expenseDto);
        } else if (expenseDto.getReceiver().getUserName().equals(user.getUserName())) {
            receivingExpenses.add(expenseDto);
        }
    }

    public Double getTotalBalance(UserContext userContext) {
        ExpensesWrapper allExpenses = getAllExpenses(userContext, null);

        double receivedSum = allExpenses.receivingExpenses.stream()
                .mapToDouble(ExpenseDto::getAmount)
                .sum();

        double payedSum = allExpenses.payedExpenses.stream()
                .mapToDouble(ExpenseDto::getAmount)
                .sum();

        return receivedSum - payedSum;
    }
}
