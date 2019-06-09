package com.sharepool.server.rest.expense.dto;

import com.sharepool.server.rest.tour.dto.TourDto;
import com.sharepool.server.rest.user.dto.UserDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "ExpenseRequest", description = "The request to make an expense for a tour.")
public class ExpenseRequestResponseDto {

    @ApiModelProperty(value = "The tours information.")
    private TourDto tour;

    @ApiModelProperty(value = "The tours owners information.")
    private UserDto receiver;

    public ExpenseRequestResponseDto() {
    }

    public ExpenseRequestResponseDto(TourDto tour, UserDto receiver) {
        this.tour = tour;
        this.receiver = receiver;
    }

    public TourDto getTour() {
        return tour;
    }

    public void setTour(TourDto tour) {
        this.tour = tour;
    }

    public UserDto getReceiver() {
        return receiver;
    }

    public void setReceiver(UserDto receiver) {
        this.receiver = receiver;
    }
}
