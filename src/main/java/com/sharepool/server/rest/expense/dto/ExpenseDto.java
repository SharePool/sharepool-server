package com.sharepool.server.rest.expense.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sharepool.server.rest.tour.dto.TourDto;
import com.sharepool.server.rest.user.dto.UserDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;
import java.util.Currency;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Expense", description = "The detailed information about an expense.")
public class ExpenseDto {

    @ApiModelProperty("The id of the expense.")
    private Long id;

    @ApiModelProperty("The creation date and time of the expense.")
    private LocalDateTime creationDate;

    private Currency currency;

    @ApiModelProperty("The amount of the expense. This is most of the time the cost for a single tour, except " +
            "if the tours prices change.")
    private double amount;

    @ApiModelProperty("The payer of the expense.")
    private UserDto payer;

    @ApiModelProperty("The receiver of the expense.")
    private UserDto receiver;

    @ApiModelProperty("The tour of the expense.")
    private TourDto tour;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public UserDto getPayer() {
        return payer;
    }

    public void setPayer(UserDto payer) {
        this.payer = payer;
    }

    public UserDto getReceiver() {
        return receiver;
    }

    public void setReceiver(UserDto receiver) {
        this.receiver = receiver;
    }

    public TourDto getTour() {
        return tour;
    }

    public void setTour(TourDto tour) {
        this.tour = tour;
    }
}
