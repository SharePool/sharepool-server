package com.sharepool.server.rest.expense.dto;

import javax.validation.constraints.NotNull;

public class ExpenseConfirmationDto {

    @NotNull
    private Long tourId;

    @NotNull
    private Long payerId;

    public ExpenseConfirmationDto() {
    }

    public ExpenseConfirmationDto(Long tourId, Long payerId) {
        this.tourId = tourId;
        this.payerId = payerId;
    }

    public Long getTourId() {
        return tourId;
    }

    public void setTourId(Long tourId) {
        this.tourId = tourId;
    }

    public Long getPayerId() {
        return payerId;
    }

    public void setPayerId(Long payerId) {
        this.payerId = payerId;
    }
}
