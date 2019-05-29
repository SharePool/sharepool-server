package com.sharepool.server.rest.expense.dto;

import javax.validation.constraints.NotNull;

public class ExpenseConfirmationDto {

    @NotNull
    private Long tourId;
    @NotNull
    private Long payerId;

    private String description;

    public ExpenseConfirmationDto() {
    }

    public ExpenseConfirmationDto(@NotNull Long tourId, @NotNull Long payerId, String description) {
        this.tourId = tourId;
        this.payerId = payerId;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
