package com.sharepool.server.rest.tour;

import com.sharepool.server.logic.tour.TourRestRequestHandler;
import com.sharepool.server.rest.tour.dto.TourDto;
import com.sharepool.server.rest.util.auth.UserContext;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Api(tags = "Tours", description = "Manage your tours.")
@RestController
@RequestMapping("tours")
public class TourResource {

	private final TourRestRequestHandler requestHandler;
	private final UserContext userContext;

	public TourResource(TourRestRequestHandler requestHandler, UserContext userContext) {
		this.requestHandler = requestHandler;
		this.userContext = userContext;
	}

	@GetMapping("users/{userId}")
	public ResponseEntity<List<TourDto>> getAllToursForUser(
			@PathVariable("userId")
			@NotNull
					Long userId
	) {
		List<TourDto> userTours = requestHandler.getAllToursForUser(userId);

		return ResponseEntity.ok(userTours);
	}

	@PostMapping
	public ResponseEntity createTour(
			@RequestBody
			@NotNull
			@Valid
					TourDto tourDto
	) {
		requestHandler.createTour(tourDto);

		System.out.println(userContext.getUserToken());

		return ResponseEntity.created(null).build();
	}

	@PutMapping("/{tourId}")
	public ResponseEntity updateTour(
			@PathVariable("tourId")
			@NotNull
					Long tourId,

			@RequestBody
			@Valid
			@NotNull
					TourDto tourDto
	) {
		requestHandler.updateTour(tourId, tourDto);

		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{tourId}")
	public ResponseEntity deleteTour(
			@PathVariable("tourId")
			@NotNull
					Long tourId
	) {
		requestHandler.deleteTour(tourId);

		return ResponseEntity.ok().build();
	}
}
