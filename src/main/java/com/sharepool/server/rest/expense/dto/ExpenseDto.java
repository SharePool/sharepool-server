package com.sharepool.server.rest.expense.dto;

import com.sharepool.server.rest.tour.dto.TourDto;
import com.sharepool.server.rest.user.dto.UserDto;

import java.time.LocalDateTime;
import java.util.Currency;

public class ExpenseDto {

    private Long id;

    private LocalDateTime creationDate;

    private Currency currency;

    private double amount;

    private UserDto receiver;

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
