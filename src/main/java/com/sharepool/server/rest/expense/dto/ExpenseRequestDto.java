package com.sharepool.server.rest.expense.dto;

import javax.validation.constraints.NotNull;

public class ExpenseRequestDto {

    @NotNull
    private Long tourId;

    public ExpenseRequestDto(@NotNull Long tourId) {
        this.tourId = tourId;
    }

    public Long getTourId() {
        return tourId;
    }

    public void setTourId(Long tourId) {
        this.tourId = tourId;
    }
}
