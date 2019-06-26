package com.sharepool.server.rest.expense.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sharepool.server.rest.user.dto.UserDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "ExpensePerUser", description = "All expenses for the current user grouped by their receiver.")
public class ExpensePerUserDto {

    @ApiModelProperty("The receiver of the expenses.")
    private UserDto receiver;

    @ApiModelProperty("The sum of the expenses from the current users point of view.")
    private double sumOfExpenses;

    @ApiModelProperty("The expenses between the current user and the receiver.")
    private List<ExpenseDto> expenses;

    public ExpensePerUserDto(UserDto receiver, double sumOfExpenses, List<ExpenseDto> expenses) {
        this.receiver = receiver;
        this.sumOfExpenses = sumOfExpenses;
        this.expenses = expenses;
    }

    public UserDto getReceiver() {
        return receiver;
    }

    public void setReceiver(UserDto receiver) {
        this.receiver = receiver;
    }

    public double getSumOfExpenses() {
        return sumOfExpenses;
    }

    public void setSumOfExpenses(double sumOfExpenses) {
        this.sumOfExpenses = sumOfExpenses;
    }

    public List<ExpenseDto> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<ExpenseDto> expenses) {
        this.expenses = expenses;
    }
}
