package com.sharepool.server.logic.tour;

import com.sharepool.server.domain.Tour;
import com.sharepool.server.rest.tour.dto.TourDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Currency;

@Mapper(componentModel = "spring")
public interface TourMapper {

    @Mapping(source = "from", target = "fromLocation")
    @Mapping(source = "to", target = "toLocation")
    @Mapping(source = "cost", target = "tourCost")
    Tour tourDtoToTour(TourDto tourDto);

    @Mapping(source = "fromLocation", target = "from")
    @Mapping(source = "toLocation", target = "to")
    @Mapping(source = "tourCost", target = "cost")
    @Mapping(source = "owner.id", target = "ownerId")
    TourDto tourToTourDto(Tour tour);

    default Tour updateTourFromDto(Tour tour, TourDto tourDto) {
        tour.setFromLocation(tourDto.getFrom());
        tour.setToLocation(tourDto.getTo());
        tour.setCurrency(Currency.getInstance(tourDto.getCurrency()));
        tour.setTourCost(tourDto.getCost());
        tour.setKilometers(tourDto.getKilometers());

		return tour;
	}
}
