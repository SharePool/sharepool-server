package com.sharepool.server.rest.expense.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value = "ExpenseWrapper", description = "A wrapper for all the users received/payed expenses.")
public class ExpensesWrapper {

    @ApiModelProperty("The users received expenses.")
    private List<ExpensePerUserDto> receivingExpenses;

    @ApiModelProperty("The users payed expenses, grouped by its receiving user.")
    private List<ExpensePerUserDto> payedExpenses;

    public ExpensesWrapper(List<ExpensePerUserDto> receivingExpenses, List<ExpensePerUserDto> payedExpenses) {
        this.receivingExpenses = receivingExpenses;
        this.payedExpenses = payedExpenses;
    }

    public List<ExpensePerUserDto> getReceivingExpenses() {
        return receivingExpenses;
    }

    public void setReceivingExpenses(List<ExpensePerUserDto> receivingExpenses) {
        this.receivingExpenses = receivingExpenses;
    }

    public List<ExpensePerUserDto> getPayedExpenses() {
        return payedExpenses;
    }

    public void setPayedExpenses(List<ExpensePerUserDto> payedExpenses) {
        this.payedExpenses = payedExpenses;
    }
}
