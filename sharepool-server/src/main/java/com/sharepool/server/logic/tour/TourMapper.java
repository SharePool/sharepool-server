package com.sharepool.server.logic.tour;

import com.sharepool.server.domain.Tour;
import com.sharepool.server.rest.tour.dto.TourDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TourMapper {

    @Mapping(source = "from", target = "fromLocation")
    @Mapping(source = "to", target = "toLocation")
    @Mapping(source = "cost", target = "tourCost")
    Tour tourDtoToTour(TourDto tourDto);

    @Mapping(source = "id", target = "tourId")
    @Mapping(source = "fromLocation", target = "from")
    @Mapping(source = "toLocation", target = "to")
    @Mapping(source = "tourCost", target = "cost")
    @Mapping(source = "owner.id", target = "ownerId")
    TourDto tourToTourDto(Tour tour);

    @Mapping(source = "from", target = "fromLocation")
    @Mapping(source = "to", target = "toLocation")
    @Mapping(source = "cost", target = "tourCost")
    void updateTourFromDto(TourDto tourDto, @MappingTarget Tour tour);
}
