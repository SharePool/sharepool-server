package com.sharepool.server.logic.tour;

import com.sharepool.server.dal.TourRepository;
import com.sharepool.server.dal.UserRepository;
import com.sharepool.server.domain.Tour;
import com.sharepool.server.domain.User;
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
        User owner = checkUserExists(userId);

        return tourRepository.findAllByOwner(owner)
                .stream()
                .map(tourMapper::tourToTourDto)
                .collect(Collectors.toList());
    }

    private User checkUserExists(Long userId) {
        Optional<User> owner = userRepository.findById(userId);
        if (!owner.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, TourRestErrorMessages.noUserFound(userId));
        }

        return owner.get();
    }

    public void createTour(TourDto tourDto) {
        User owner = checkUserExists(tourDto.getOwnerId());

        Tour tour = tourMapper.tourDtoToTour(tourDto);
        tour.setOwner(owner);

        tourRepository.save(tour);
    }

    public void updateTour(Long tourId, TourDto tourDto) {
        Tour tour = checkTourExists(tourId);

        tourMapper.updateTourFromDto(tourDto, tour);

        tourRepository.save(tour);
    }

    private Tour checkTourExists(Long tourId) {
        Optional<Tour> optionalTour = tourRepository.findById(tourId);
        if (!optionalTour.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, TourRestErrorMessages.noTourFound(tourId));
        }

        return optionalTour.get();
    }

    public void deleteTour(Long tourId) {
        Tour tour = checkTourExists(tourId);

        tourRepository.delete(tour);
    }
}
