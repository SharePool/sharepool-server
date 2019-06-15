package com.sharepool.server.rest.tour.dto;

import com.sharepool.server.rest.tour.validation.IsValidCurrency;
import com.sharepool.server.rest.tour.validation.LocationsNotEqual;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@ApiModel(value = "Tour", description = "The detailed information of the tour.")
@LocationsNotEqual
public class TourDto {

	@ApiModelProperty(value = "The tours unique id.")
	private Long tourId;

	@ApiModelProperty(value = "The tours starting location.", required = true)
	@NotNull
	private String from;

	@ApiModelProperty(value = "The tours ending location.", required = true)
	@NotNull
	private String to;

	@ApiModelProperty(value = "The currency for the tours cost.", required = true)
	@NotNull
	@IsValidCurrency
	private String currency;

	@ApiModelProperty(value = "The cost of the tour.", required = true)
	@DecimalMin("0.1")
	private double cost;

	@ApiModelProperty(value = "The kilometers between the 2 locations.", required = true)
	private double kilometers;

    @ApiModelProperty(value = "The tours owners unique id.\n" +
            "This parameter may not be set when creating/updating a tour.", required = true)
    @Null
	private Long ownerId;

    @ApiModelProperty("Specifies whether this tour has been deactivated by the owner.\n" +
            "This parameter may not be set when creating/updating a tour.")
    @Null
    private Boolean active;

	public Long getTourId() {
		return tourId;
	}

	public void setTourId(Long tourId) {
		this.tourId = tourId;
	}

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

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
