package com.sharepool.server.rest.expense.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value = "ExpenseWrapper", description = "A wrapper for all the users received/payed expenses.")
public class ExpensesWrapper {

    @ApiModelProperty("The users total balance (for the current query if filtered).")
    private double totalBalance;

    @ApiModelProperty("The users expenses.")
    private List<ExpensePerUserDto> expenses;

    public ExpensesWrapper(List<ExpensePerUserDto> expenses, double totalBalance) {
        this.expenses = expenses;
        this.totalBalance = totalBalance;
    }

    public List<ExpensePerUserDto> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<ExpensePerUserDto> expenses) {
        this.expenses = expenses;
    }

    public double getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(double totalBalance) {
        this.totalBalance = totalBalance;
    }
}
