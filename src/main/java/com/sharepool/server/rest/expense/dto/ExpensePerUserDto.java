package com.sharepool.server.rest.expense.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sharepool.server.rest.user.dto.UserDto;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExpensePerUserDto {

    private UserDto receiver;

    private double sumOfExpenses;

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
