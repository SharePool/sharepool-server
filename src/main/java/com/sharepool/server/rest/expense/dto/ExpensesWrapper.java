package com.sharepool.server.rest.expense.dto;

import java.util.List;

public class ExpensesWrapper {

    public List<ExpenseDto> receivingExpenses;

    public List<ExpenseDto> payedExpenses;

    public ExpensesWrapper(List<ExpenseDto> receivingExpenses, List<ExpenseDto> payedExpenses) {
        this.receivingExpenses = receivingExpenses;
        this.payedExpenses = payedExpenses;
    }

    public List<ExpenseDto> getReceivingExpenses() {
        return receivingExpenses;
    }

    public void setReceivingExpenses(List<ExpenseDto> receivingExpenses) {
        this.receivingExpenses = receivingExpenses;
    }

    public List<ExpenseDto> getPayedExpenses() {
        return payedExpenses;
    }

    public void setPayedExpenses(List<ExpenseDto> payedExpenses) {
        this.payedExpenses = payedExpenses;
    }
}
