package com.sharepool.server.logic.tour;

import com.sharepool.server.dal.TourRepository;
import com.sharepool.server.dal.UserRepository;
import com.sharepool.server.domain.Tour;
import com.sharepool.server.rest.tour.dto.TourDto;
import com.sharepool.server.rest.util.RestHelperUtil;
import com.sharepool.server.rest.util.auth.UserContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TourRestRequestHandler {

    private final UserRepository userRepository;
    private final TourRepository tourRepository;
    private final TourMapper tourMapper;

    public TourRestRequestHandler(
            UserRepository userRepository,
            TourRepository tourRepository,
            TourMapper tourMapper
    ) {
        this.userRepository = userRepository;
        this.tourRepository = tourRepository;
        this.tourMapper = tourMapper;
    }

    public List<TourDto> getAllToursForUser(UserContext userContext, boolean includeInactive) {
        if (includeInactive) {
            return tourRepository.findAllByOwner(userContext.getUser())
                    .stream()
                    .map(tourMapper::tourToTourDto)
                    .collect(Collectors.toList());
        }

        return tourRepository.findAllByOwnerAndActiveIsTrue(userContext.getUser())
                .stream()
                .map(tourMapper::tourToTourDto)
                .collect(Collectors.toList());
    }

    public Tour createTour(TourDto tourDto, UserContext userContext) {
        Tour tour = tourMapper.tourDtoToTour(tourDto);
        tour.setOwner(userContext.getUser());
        tour.setActive(true);

        return tourRepository.save(tour);
    }

    public void updateTour(Long tourId, TourDto tourDto, UserContext userContext) {
        Tour tour = RestHelperUtil.checkExists(
                () -> tourRepository.findByIdAndOwner(tourId, userContext.getUser()), tourId, Tour.class);

        tourMapper.updateTourFromDto(tourDto, tour);

        tourRepository.save(tour);
    }

    public void deleteTour(Long tourId, UserContext userContext) {
        Tour tour = RestHelperUtil.checkExists(
                () -> tourRepository.findByIdAndOwner(tourId, userContext.getUser()), tourId, Tour.class);

        tour.setActive(false);
        tourRepository.save(tour);
    }

    public void activateTour(Long tourId, UserContext userContext) {
        Tour tour = RestHelperUtil.checkExists(
                () -> tourRepository.findByIdAndOwner(tourId, userContext.getUser()), tourId, Tour.class);

        tour.setActive(true);
        tourRepository.save(tour);
    }
}
