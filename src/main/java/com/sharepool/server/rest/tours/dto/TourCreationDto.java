package com.sharepool.server.rest.tours.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import com.sharepool.server.rest.tours.dto.validation.LocationsNotEqual;

@LocationsNotEqual
public class TourCreationDto {

	@NotNull
	private String from;

	@NotNull
	private String to;

	@NotNull
	private String currency;

	@DecimalMin("0.1")
	private double cost;

	private double kilometers;

	@NotNull
	private Long ownerId;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public double getKilometers() {
		return kilometers;
	}

	public void setKilometers(double kilometers) {
		this.kilometers = kilometers;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}
}
