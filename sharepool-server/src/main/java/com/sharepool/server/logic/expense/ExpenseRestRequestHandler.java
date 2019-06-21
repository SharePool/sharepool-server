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
import com.sharepool.server.rest.util.RestHelperUtil;
import com.sharepool.server.rest.util.auth.UserContext;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<ExpenseDto> getAllExpenses(UserContext userContext, Long receiverId) {
        if (receiverId != null) {
            User receiver = RestHelperUtil.checkExists(userRepository, receiverId, User.class);

            return expenseRepository.findAllByPayerAndReceiver(userContext.getUser(), receiver)
                    .stream()
                    .map(expenseMapper::expenseToExpenseDto)
                    .collect(Collectors.toList());
        }

        return expenseRepository.findAllByPayer(userContext.getUser())
                .stream()
                .map(expenseMapper::expenseToExpenseDto)
                .collect(Collectors.toList());
    }
}
