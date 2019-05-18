package com.sharepool.server.domain;

import java.time.LocalDate;
import java.util.Currency;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Expense {

	@Id
	@GeneratedValue
	private Long id;

	private String description;

	private LocalDate creationDate;

	private Currency currency;

	private double amount;

	@ManyToOne(cascade = CascadeType.ALL)
	private AppUser payer;

	@ManyToOne(cascade = CascadeType.ALL)
	private AppUser receiver;

	@ManyToOne(cascade = CascadeType.ALL)
	private Tour tour;

	public Expense() {
	}

	public Expense(
			String description,
			LocalDate creationDate,
			Currency currency,
			double amount,
			AppUser payer,
			AppUser receiver,
			Tour tour
	) {
		this.description = description;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
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

	public AppUser getPayer() {
		return payer;
	}

	public void setPayer(AppUser payer) {
		this.payer = payer;
	}

	public AppUser getReceiver() {
		return receiver;
	}

	public void setReceiver(AppUser receiver) {
		this.receiver = receiver;
	}

	public Tour getTour() {
		return tour;
	}

	public void setTour(Tour tour) {
		this.tour = tour;
	}
}