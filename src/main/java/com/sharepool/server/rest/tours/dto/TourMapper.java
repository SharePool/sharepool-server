package com.sharepool.server.rest.tours.dto;

import java.util.Currency;

import org.springframework.stereotype.Component;

import com.sharepool.server.domain.Tour;

@Component
public class TourMapper {

	public Tour tourCreationDtoToTour(TourCreationDto tourCreationDto) {
		Tour tour = new Tour();

		tour.setFromLocation(tourCreationDto.getFrom());
		tour.setToLocation(tourCreationDto.getTo());
		tour.setCurrency(Currency.getInstance(tourCreationDto.getCurrency()));
		tour.setTourCost(tourCreationDto.getCost());
		tour.setKilometers(tourCreationDto.getKilometers());

		return tour;
	}
}
