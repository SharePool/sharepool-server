package com.sharepool.server.rest.tours;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.sharepool.server.dal.AppUserRepository;
import com.sharepool.server.dal.TourRepository;
import com.sharepool.server.domain.AppUser;
import com.sharepool.server.domain.Tour;
import com.sharepool.server.rest.tours.dto.TourCreationDto;
import com.sharepool.server.rest.tours.dto.TourMapper;

@RestController
@RequestMapping("tours")
public class TourResource {

	private final AppUserRepository userRepository;
	private final TourRepository tourRepository;
	private final TourMapper tourMapper;

	public TourResource(
			AppUserRepository userRepository,
			TourRepository tourRepository,
			TourMapper tourMapper
	) {
		this.userRepository = userRepository;
		this.tourRepository = tourRepository;
		this.tourMapper = tourMapper;
	}

	@GetMapping("users/{userId}")
	public ResponseEntity<List<Tour>> getAllToursForUser(
			@PathVariable("userId")
			@NotNull
					Long userId
	) {
		Optional<AppUser> user = userRepository.findById(userId);
		if (!user.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, TourRestErrorMessages.noUserFound(userId));
		}

		List<Tour> userTours = tourRepository.findAllByOwner(user.get());

		return ResponseEntity.ok(userTours);
	}

	@PostMapping
	public ResponseEntity createTour(
			@RequestBody
			@NotNull
			@Valid
					TourCreationDto tourCreationDto
	) {
		Optional<AppUser> owner = userRepository.findById(tourCreationDto.getOwnerId());
		if (!owner.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, TourRestErrorMessages.noUserFound(tourCreationDto.getOwnerId()));
		}

		tourRepository.save(tourMapper.tourCreationDtoToTour(tourCreationDto));

		return ResponseEntity.created(null).build();
	}

	@PostMapping("/{tourId}")
	public ResponseEntity updateTour(
			@PathVariable("tourId")
			@NotNull
					Long tourId,

			@RequestBody
			@Valid
			@NotNull
					TourCreationDto tourCreationDto
	) {
		Optional<Tour> optionalTour = tourRepository.findById(tourId);
		if (!optionalTour.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, TourRestErrorMessages.noTourFound(tourId));
		}

		Optional<AppUser> owner = userRepository.findById(tourCreationDto.getOwnerId());
		if (!owner.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, TourRestErrorMessages.noUserFound(tourCreationDto.getOwnerId()));
		}

		Tour tour = optionalTour.get();
		tourMapper.updateTourFromDto(tour, tourCreationDto);
		tourRepository.save(tour);

		return ResponseEntity.ok().build();
	}
}
