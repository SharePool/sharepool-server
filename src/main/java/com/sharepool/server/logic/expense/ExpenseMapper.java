package com.sharepool.server.logic.expense;

import com.sharepool.server.domain.Expense;
import com.sharepool.server.domain.Tour;
import com.sharepool.server.domain.User;
import com.sharepool.server.logic.tour.TourMapper;
import com.sharepool.server.logic.user.UserMapper;
import com.sharepool.server.rest.expense.dto.ExpenseDto;
import com.sharepool.server.rest.expense.dto.ExpenseRequestResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {
        UserMapper.class,
        TourMapper.class
})
public interface ExpenseMapper {

    ExpenseRequestResponseDto userAndTourToExpenseRequestResponseDto(User receiver, Tour tour);

    ExpenseDto expenseToExpenseDto(Expense expense);
}
