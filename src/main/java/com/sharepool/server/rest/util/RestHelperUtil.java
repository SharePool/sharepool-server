package com.sharepool.server.rest.util;

import com.sharepool.server.dal.TourRepository;
import com.sharepool.server.dal.UserRepository;
import com.sharepool.server.domain.Tour;
import com.sharepool.server.domain.User;
import com.sharepool.server.rest.tour.TourRestErrorMessages;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public class RestHelperUtil {

    private RestHelperUtil() {
    }

    public static Tour checkTourExists(TourRepository tourRepository, Long tourId) {
        Optional<Tour> optionalTour = tourRepository.findById(tourId);
        if (!optionalTour.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, TourRestErrorMessages.noTourFound(tourId));
        }

        return optionalTour.get();
    }

    public static User checkUserExists(UserRepository userRepository, Long userId) {
        Optional<User> owner = userRepository.findById(userId);
        if (!owner.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, TourRestErrorMessages.noUserFound(userId));
        }

        return owner.get();
    }
}
