package com.sharepool.server.logic.expense;

import com.sharepool.server.dal.ExpenseRepository;
import com.sharepool.server.dal.TourRepository;
import com.sharepool.server.dal.UserRepository;
import com.sharepool.server.domain.Expense;
import com.sharepool.server.domain.Tour;
import com.sharepool.server.domain.User;
import com.sharepool.server.rest.expense.dto.ExpenseConfirmationDto;
import com.sharepool.server.rest.expense.dto.ExpenseRequestResponseDto;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Optional;

@Component
public class ExpenseRestRequestHandler {

    private final TourRepository tourRepository;
    private final UserRepository userRepository;
    private final ExpenseRepository expenseRepository;

    private final ExpenseMapper expenseMapper;

    public ExpenseRestRequestHandler(TourRepository tourRepository, ExpenseMapper expenseMapper, UserRepository userRepository, ExpenseRepository expenseRepository) {
        this.tourRepository = tourRepository;
        this.expenseMapper = expenseMapper;
        this.userRepository = userRepository;
        this.expenseRepository = expenseRepository;
    }

    public ExpenseRequestResponseDto requestExpense(Long tourId) {
        Optional<Tour> tour = tourRepository.findById(tourId);

        if (tour.isPresent()) {
            User owner = tour.get().getOwner();

            if (owner != null) {
                return expenseMapper.userAndTourToExpenseRequestResponseDto(owner, tour.get());
            }
        }

        return null;
    }

    public boolean confirmExpense(@Valid ExpenseConfirmationDto expenseConfirmationDto) {

        Optional<Tour> tourOptional = tourRepository.findById(expenseConfirmationDto.getTourId());

        if (tourOptional.isPresent()) {
            Tour tour = tourOptional.get();
            User receiver = tour.getOwner();
            Optional<User> payerOptional = userRepository.findById(expenseConfirmationDto.getPayerId());

            if (payerOptional.isPresent()) {
                User payer = payerOptional.get();
                Expense expense = new Expense(expenseConfirmationDto.getDescription(), LocalDate.now(), tour.getCurrency(), tour.getTourCost(), payer, receiver, tour);
                expenseRepository.save(expense);

                return true;
            }
        }
        return false;
    }
}
