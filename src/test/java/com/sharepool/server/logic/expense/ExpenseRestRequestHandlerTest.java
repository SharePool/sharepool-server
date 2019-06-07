package com.sharepool.server.logic.expense;

import com.sharepool.server.common.AbstractUtilTest;
import com.sharepool.server.dal.ExpenseRepository;
import com.sharepool.server.dal.TourRepository;
import com.sharepool.server.dal.UserRepository;
import com.sharepool.server.domain.Expense;
import com.sharepool.server.domain.Tour;
import com.sharepool.server.domain.User;
import com.sharepool.server.rest.expense.dto.ExpenseConfirmationDto;
import com.sharepool.server.rest.expense.dto.ExpenseRequestResponseDto;
import com.sharepool.server.rest.tour.TourRestErrorMessages;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ExpenseRestRequestHandlerTest extends AbstractUtilTest {

    @Autowired
    private ExpenseRestRequestHandler expenseRestRequestHandler;
    @Autowired
    private TourRepository tourRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ExpenseRepository expenseRepository;

    @Test
    public void requestValidExpense() {
        User user = userRepository.save(createValidUser());
        Tour tour = tourRepository.save(createValidTour(user));

        ExpenseRequestResponseDto response = expenseRestRequestHandler.requestExpense(tour.getId());

        Assert.assertEquals(tour.getId(), response.getTour().getTourId());
        Assert.assertEquals(user.getUserName(), response.getReceiver().getUserName());
    }

    @Test
    public void requestInvalidExpense() {
        User user = createValidUser();
        user.setId(1L);
        Tour tour = createValidTour(user);
        tour.setId(1L);

        assertThrows(ResponseStatusException.class,
                () -> expenseRestRequestHandler.requestExpense(tour.getId()),
                TourRestErrorMessages.noTourFound(tour.getId()));
    }

    @Test
    public void confirmValidExpense() {
        User receiver = userRepository.save(createValidUser());
        Tour tour = tourRepository.save(createValidTour(receiver));

        User payer = userRepository.save(createValidUser());

        expenseRestRequestHandler.confirmExpense(new ExpenseConfirmationDto(tour.getId(), payer.getId()));

        List<Expense> expensesForReceiver = expenseRepository.findAllByReceiver(receiver);
        Assert.assertEquals(1, expensesForReceiver.size());

        Expense expense = expensesForReceiver.get(0);
        Assert.assertEquals(payer, expense.getPayer());
        Assert.assertEquals(receiver, expense.getReceiver());
        Assert.assertEquals(tour.getTourCost(), expense.getAmount(), 0.0);
        Assert.assertEquals(LocalDate.now(), expense.getCreationDate());
        Assert.assertEquals(tour.getCurrency(), expense.getCurrency());
    }

    @Test
    public void confirmInvalidExpense() {
        User user = createValidUser();
        user.setId(1L);
        Tour tour = createValidTour(user);
        tour.setId(1L);

        assertThrows(ResponseStatusException.class,
                () -> expenseRestRequestHandler.confirmExpense(new ExpenseConfirmationDto(tour.getId(), user.getId())));
    }
}