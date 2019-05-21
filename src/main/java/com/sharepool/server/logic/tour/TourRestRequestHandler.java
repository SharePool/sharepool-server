package com.sharepool.server.logic.tour;

import com.sharepool.server.dal.AppUserRepository;
import com.sharepool.server.dal.TourRepository;
import com.sharepool.server.domain.AppUser;
import com.sharepool.server.domain.Tour;
import com.sharepool.server.rest.tour.TourRestErrorMessages;
import com.sharepool.server.rest.tour.dto.TourDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TourRestRequestHandler {

    private final AppUserRepository userRepository;
    private final TourRepository tourRepository;
    private final TourMapper tourMapper;

    public TourRestRequestHandler(
            AppUserRepository userRepository,
            TourRepository tourRepository,
            TourMapper tourMapper
    ) {
        this.userRepository = userRepository;
        this.tourRepository = tourRepository;
        this.tourMapper = tourMapper;
    }

    public List<TourDto> getAllToursForUser(Long userId) {
        AppUser owner = checkUserExists(userId);

        return tourRepository.findAllByOwner(owner)
                .stream()
                .map(tourMapper::tourToTourDto)
                .collect(Collectors.toList());
    }

    private AppUser checkUserExists(Long userId) {
        Optional<AppUser> owner = userRepository.findById(userId);
        if (!owner.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, TourRestErrorMessages.noUserFound(userId));
        }

        return owner.get();
    }

    public void createTour(TourDto tourDto) {
        AppUser owner = checkUserExists(tourDto.getOwnerId());

        Tour tour = tourMapper.tourDtoToTour(tourDto);
        tour.setOwner(owner);

        tourRepository.save(tour);
    }

    public void updateTour(Long tourId, TourDto tourDto) {
        Tour tour = checkTourExists(tourId);
        AppUser owner = checkUserExists(tourDto.getOwnerId());

        tourMapper.updateTourFromDto(tour, tourDto);
        tour.setOwner(owner);

        tourRepository.save(tour);
    }

    private Tour checkTourExists(Long tourId) {
        Optional<Tour> optionalTour = tourRepository.findById(tourId);
        if (!optionalTour.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, TourRestErrorMessages.noTourFound(tourId));
        }

        return optionalTour.get();
    }
}
