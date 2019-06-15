package com.sharepool.server.rest.tour;

import com.sharepool.server.domain.Tour;
import com.sharepool.server.logic.tour.TourRestRequestHandler;
import com.sharepool.server.rest.tour.dto.TourDto;
import com.sharepool.server.rest.util.HATEOASPlaceholder;
import com.sharepool.server.rest.util.auth.UserContext;
import io.swagger.annotations.Api;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

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

    @GetMapping
    public ResponseEntity<List<TourDto>> getAllToursForUser() {
        List<TourDto> userTours = requestHandler.getAllToursForUser(userContext.getUser().getId());

		return ResponseEntity.ok(userTours);
	}

	@PostMapping
	public ResponseEntity<Resource<HATEOASPlaceholder>> createTour(
			@RequestBody
			@NotNull
			@Valid
					TourDto tourDto
	) {
        Tour tour = requestHandler.createTour(tourDto, userContext);

		return ResponseEntity.created(null).body(
				new Resource<>(
						new HATEOASPlaceholder(),
						linkTo(TourResource.class).slash(tour.getId()).withSelfRel()));
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
        requestHandler.updateTour(tourId, tourDto, userContext);

		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{tourId}")
	public ResponseEntity deleteTour(
			@PathVariable("tourId")
			@NotNull
					Long tourId
	) {
        requestHandler.deleteTour(tourId, userContext);

		return ResponseEntity.ok().build();
	}
}
