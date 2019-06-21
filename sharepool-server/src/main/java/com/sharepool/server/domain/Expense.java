package com.sharepool.server.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Currency;

@Entity
public class Expense {

	@Id
	@GeneratedValue
	private Long id;

    private LocalDateTime creationDate;

	private Currency currency;

	private double amount;

	@ManyToOne(cascade = CascadeType.ALL)
	private User payer;

	@ManyToOne(cascade = CascadeType.ALL)
	private User receiver;

	@ManyToOne(cascade = CascadeType.ALL)
	private Tour tour;

	public Expense() {
	}

	public Expense(
            LocalDateTime creationDate,
            Currency currency,
            double amount,
            User payer,
            User receiver,
            Tour tour
	) {
		this.creationDate = creationDate;
		this.currency = currency;
		this.amount = amount;
		this.payer = payer;
		this.receiver = receiver;
		this.tour = tour;
	}

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

	public User getPayer() {
		return payer;
	}

	public void setPayer(User payer) {
		this.payer = payer;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}

	public Tour getTour() {
		return tour;
	}

	public void setTour(Tour tour) {
		this.tour = tour;
	}
}
