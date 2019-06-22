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
import java.util.Currency;
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

    public ExpensesWrapper getAllExpenses(UserContext userContext, Long receiverId) {
        List<ExpensePerUserDto> expenses = new ArrayList<>();

        User user = userContext.getUser();
        if (receiverId != null) {
            User receiver = RestHelperUtil.checkExists(userRepository, receiverId, User.class);

            expenseRepository.findAllByPayerAndReceiverOrderByCreationDateDesc(user, receiver)
                    .stream()
                    .map(expenseMapper::expenseToExpenseDto)
                    .forEach(e -> createOrAddToUser(user, expenses, e));

            return new ExpensesWrapper(expenses, expenses.stream().mapToDouble(ExpensePerUserDto::getSumOfExpenses).sum());
        }

        expenseRepository.findAllByPayerOrReceiverOrderByCreationDateDesc(user, user)
                .stream()
                .map(expenseMapper::expenseToExpenseDto)
                .forEach(e -> createOrAddToUser(user, expenses, e));

        return new ExpensesWrapper(expenses, expenses.stream().mapToDouble(ExpensePerUserDto::getSumOfExpenses).sum());
    }

    private void createOrAddToUser(User user, List<ExpensePerUserDto> expenses, ExpenseDto expense) {
        // don't track for ourselves, rather update the value for the other user
        boolean userIsReceiver = expense.getReceiver().getUserName().equals(user.getUserName());

        UserDto receiver = userIsReceiver ? expense.getPayer() : expense.getReceiver();

        // but still book from user point of view
        double amount = userIsReceiver ? expense.getAmount() : expense.getAmount() * -1;
        expense.setAmount(userIsReceiver ? expense.getAmount() : expense.getAmount() * -1);

        // if the mapping already holds a field update the value
        for (ExpensePerUserDto expensePerUser : expenses) {
            if (expensePerUser.getReceiver().getUserName().equals(receiver.getUserName())) {
                expensePerUser.setSumOfExpenses(expensePerUser.getSumOfExpenses() + amount);

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

    public void createPayback(UserContext userContext, PaybackDto paybackDto) {
        User receiver = userRepository.findByUserNameOrEmail(
                paybackDto.getUserNameOrEmail(),
                paybackDto.getUserNameOrEmail()
        ).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Receiving user not found."));

        Expense expense = new Expense();
        expense.setPayer(userContext.getUser());
        expense.setCreationDate(LocalDateTime.now());
        expense.setReceiver(receiver);
        expense.setCurrency(Currency.getInstance("EUR"));
        expense.setAmount(paybackDto.getAmount() * -1);

        expenseRepository.save(expense);
    }
}
