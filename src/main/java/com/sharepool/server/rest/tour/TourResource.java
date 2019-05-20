package com.sharepool.server.rest.tour;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sharepool.server.domain.Tour;
import com.sharepool.server.logic.tour.TourRestRequestHandler;
import com.sharepool.server.rest.tour.dto.TourCreationDto;

@RestController
@RequestMapping("tours")
public class TourResource {

	private final TourRestRequestHandler requestHandler;

	public TourResource(TourRestRequestHandler requestHandler) {this.requestHandler = requestHandler;}

	@GetMapping("users/{userId}")
	public ResponseEntity<List<Tour>> getAllToursForUser(
			@PathVariable("userId")
			@NotNull
					Long userId
	) {
		List<Tour> userTours = requestHandler.getAllToursForUser(userId);

		return ResponseEntity.ok(userTours);
	}

	@PostMapping
	public ResponseEntity createTour(
			@RequestBody
			@NotNull
			@Valid
					TourCreationDto tourCreationDto
	) {
		requestHandler.createTour(tourCreationDto);

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
		requestHandler.updateTour(tourId, tourCreationDto);

		return ResponseEntity.ok().build();
	}
}
