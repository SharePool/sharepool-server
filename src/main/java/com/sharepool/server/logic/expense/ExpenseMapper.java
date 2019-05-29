package com.sharepool.server.logic.expense;

import com.sharepool.server.domain.Tour;
import com.sharepool.server.domain.User;
import com.sharepool.server.rest.expense.dto.ExpenseRequestResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExpenseMapper {

    // TODO: check why implicit mapping doesn't work (both mappers are defined but something goes wrong)
//    @Mapping(source = "user", target = "userDto")
//    @Mapping(source = "tour", target = "tourDto")
    @Mapping(source = "user.firstName", target = "userDto.firstName")
    @Mapping(source = "user.lastName", target = "userDto.lastName")
    @Mapping(source = "user.userName", target = "userDto.userName")
    @Mapping(source = "tour.id", target = "tourDto.tourId")
    @Mapping(source = "tour.fromLocation", target = "tourDto.from")
    @Mapping(source = "tour.toLocation", target = "tourDto.to")
    ExpenseRequestResponseDto userAndTourToExpenseRequestResponseDto(User user, Tour tour);
}
