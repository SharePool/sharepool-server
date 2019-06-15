package com.sharepool.server.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Currency;

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

	private boolean active;

	@ManyToOne
	private User owner;

	public Tour() {
	}

	public Tour(
			String fromLocation,
			String toLocation,
			double kilometers,
			Currency currency,
			double tourCost,
			User owner
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

	public double getKilometers() {
		return kilometers;
	}

	public void setKilometers(double kilometers) {
		this.kilometers = kilometers;
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

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
