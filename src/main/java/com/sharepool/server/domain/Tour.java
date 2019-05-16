package com.sharepool.server.domain;

import java.util.Currency;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Tour {

	@Id
	@GeneratedValue
	private Long id;

	private String fromLocation;

	private String toLocation;

	private double kilometers;

	private Currency currency;

	private double tourCost;

	@ManyToOne
	private AppUser owner;

	public Tour() {
	}

	public Tour(
			String fromLocation,
			String toLocation,
			double kilometers,
			Currency currency,
			double tourCost,
			AppUser owner
	) {
		this.fromLocation = fromLocation;
		this.toLocation = toLocation;
		this.kilometers = kilometers;
		this.currency = currency;
		this.tourCost = tourCost;
		this.owner = owner;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFromLocation() {
		return fromLocation;
	}

	public void setFromLocation(String fromLocation) {
		this.fromLocation = fromLocation;
	}

	public String getToLocation() {
		return toLocation;
	}

	public void setToLocation(String toLocation) {
		this.toLocation = toLocation;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public double getTourCost() {
		return tourCost;
	}

	public void setTourCost(double tourCost) {
		this.tourCost = tourCost;
	}

	public AppUser getOwner() {
		return owner;
	}

	public void setOwner(AppUser owner) {
		this.owner = owner;
	}
}
