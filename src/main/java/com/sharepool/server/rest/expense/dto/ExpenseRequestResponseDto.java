package com.sharepool.server.rest.expense.dto;

import com.sharepool.server.rest.tour.dto.TourDto;
import com.sharepool.server.rest.user.dto.UserDto;

public class ExpenseRequestResponseDto {

    private TourDto tourDto;
    private UserDto userDto;

    public ExpenseRequestResponseDto() {
    }

    public ExpenseRequestResponseDto(TourDto tourDto, UserDto userDto) {
        this.tourDto = tourDto;
        this.userDto = userDto;
    }

    public TourDto getTourDto() {
        return tourDto;
    }

    public void setTourDto(TourDto tourDto) {
        this.tourDto = tourDto;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }
}
