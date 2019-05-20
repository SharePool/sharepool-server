package com.sharepool.server.logic.tour;

import java.util.Currency;

import org.springframework.stereotype.Component;

import com.sharepool.server.domain.Tour;
import com.sharepool.server.rest.tour.dto.TourCreationDto;

@Component
public class TourMapper {

	public Tour tourCreationDtoToTour(TourCreationDto tourCreationDto) {
		return updateTourFromDto(null, tourCreationDto);
	}

	public Tour updateTourFromDto(Tour tour, TourCreationDto tourCreationDto) {
		if (tour == null) {
			tour = new Tour();
		}

		tour.setFromLocation(tourCreationDto.getFrom());
		tour.setToLocation(tourCreationDto.getTo());
		tour.setCurrency(Currency.getInstance(tourCreationDto.getCurrency()));
		tour.setTourCost(tourCreationDto.getCost());
		tour.setKilometers(tourCreationDto.getKilometers());

		return tour;
	}
}
