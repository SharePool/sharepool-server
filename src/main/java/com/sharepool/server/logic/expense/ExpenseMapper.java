package com.sharepool.server.logic.expense;

import com.sharepool.server.domain.Tour;
import com.sharepool.server.domain.User;
import com.sharepool.server.logic.tour.TourMapper;
import com.sharepool.server.logic.user.UserMapper;
import com.sharepool.server.rest.expense.dto.ExpenseRequestResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {
        UserMapper.class,
        TourMapper.class
})
public interface ExpenseMapper {

    @Mapping(source = "receiver", target = "receiver")
    @Mapping(source = "tour", target = "tour")
    ExpenseRequestResponseDto userAndTourToExpenseRequestResponseDto(User receiver, Tour tour);
}
