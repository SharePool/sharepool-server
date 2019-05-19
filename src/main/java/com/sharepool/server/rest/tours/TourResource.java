package com.sharepool.server.rest.tours;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
			return ResponseEntity.notFound().build();
		}

		List<Tour> userTours = tourRepository.findAllByOwner(user.get());

		return ResponseEntity.ok(userTours);
	}

	@PostMapping
	public ResponseEntity createTour(
			@RequestBody
			@NotNull
			@Valid
					TourCreationDto dto
	) {
		tourRepository.save(tourMapper.tourCreationDtoToTour(dto));

		return ResponseEntity.created(null).build();
	}

	@PostMapping("/{id}")
	public ResponseEntity updateTour(
			@PathVariable("id")
			@NotNull
					Long id,

			@RequestBody
			@NotNull
			@Valid
					TourCreationDto dto
	) {
		Optional<Tour> tour = tourRepository.findById(id);
		if (!tour.isPresent()) {
			return ResponseEntity.notFound().build();
		}

		tourRepository.save(tour.get());

		return ResponseEntity.ok().build();
	}
}
