package com.sharepool.server.logic.expense;

import com.sharepool.server.domain.Expense;
import com.sharepool.server.domain.Tour;
import com.sharepool.server.domain.User;
import com.sharepool.server.logic.tour.TourMapper;
import com.sharepool.server.logic.user.UserMapper;
import com.sharepool.server.rabbitmq.AnalyticsMessage;
import com.sharepool.server.rest.expense.dto.ExpenseDto;
import com.sharepool.server.rest.expense.dto.ExpenseRequestResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring", uses = {
        UserMapper.class,
        TourMapper.class
})
public interface ExpenseMapper {

    ExpenseRequestResponseDto userAndTourToExpenseRequestResponseDto(User receiver, Tour tour);

    ExpenseDto expenseToExpenseDto(Expense expense);

    @Mapping(source = "id", target = "expenseId")
    @Mapping(source = "tour.id", target = "tourId")
    @Mapping(source = "payer.id", target = "payerId")
    @Mapping(source = "tour.kilometers", target = "kilometers")
    @Mapping(source = "creationDate", target = "creationTimestamp")
    AnalyticsMessage expenseToAnalyticsMessage(Expense expense);

    default Long localDateTimeToUTCTimestamp(LocalDateTime localDateTime) {
        return localDateTime.toEpochSecond(ZoneOffset.UTC);
    }
}
