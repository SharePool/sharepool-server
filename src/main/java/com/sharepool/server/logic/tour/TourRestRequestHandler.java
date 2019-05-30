package com.sharepool.server.logic.tour;

import com.sharepool.server.dal.TourRepository;
import com.sharepool.server.dal.UserRepository;
import com.sharepool.server.domain.Tour;
import com.sharepool.server.domain.User;
import com.sharepool.server.rest.tour.dto.TourDto;
import com.sharepool.server.rest.util.RestHelperUtil;
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

    public List<TourDto> getAllToursForUser(Long userId) {
        User owner = RestHelperUtil.checkUserExists(userRepository, userId);

        return tourRepository.findAllByOwner(owner)
                .stream()
                .map(tourMapper::tourToTourDto)
                .collect(Collectors.toList());
    }

    public void createTour(TourDto tourDto) {
        User owner = RestHelperUtil.checkUserExists(userRepository, tourDto.getOwnerId());

        Tour tour = tourMapper.tourDtoToTour(tourDto);
        tour.setOwner(owner);

        tourRepository.save(tour);
    }

    public void updateTour(Long tourId, TourDto tourDto) {
        Tour tour = RestHelperUtil.checkTourExists(tourRepository, tourId);

        tourMapper.updateTourFromDto(tourDto, tour);

        tourRepository.save(tour);
    }

    public void deleteTour(Long tourId) {
        Tour tour = RestHelperUtil.checkTourExists(tourRepository, tourId);

        tourRepository.delete(tour);
    }
}
