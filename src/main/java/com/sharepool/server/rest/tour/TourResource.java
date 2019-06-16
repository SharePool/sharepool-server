package com.sharepool.server.rest.tour;

import com.sharepool.server.domain.Tour;
import com.sharepool.server.logic.tour.TourRestRequestHandler;
import com.sharepool.server.rest.tour.dto.TourDto;
import com.sharepool.server.rest.util.HATEOASPlaceholder;
import com.sharepool.server.rest.util.auth.UserContext;
import io.swagger.annotations.*;
import org.springframework.cache.annotation.CachePut;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.Cacheable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Api(tags = "Tours", description = "Manage your tours.")
@RestController
@RequestMapping("tours")
public class TourResource {

	private static final String TOURS_CACHE_NAME = "tours";

	private final TourRestRequestHandler requestHandler;
	private final UserContext userContext;

	public TourResource(TourRestRequestHandler requestHandler, UserContext userContext) {
		this.requestHandler = requestHandler;
		this.userContext = userContext;
	}

	@ApiOperation(
			value = "Lists all tours for the logged in user."
	)
	@ApiResponses({
			@ApiResponse(code = 200, message = "Success. The response contains the users tours."),
			@ApiResponse(code = 500, message = "Failed. Something went wrong on our side."),
	})
	@GetMapping
	@Cacheable(TOURS_CACHE_NAME)
	public ResponseEntity<List<TourDto>> getAllToursForUser(
			@ApiParam("Whether inactive tours should be included or not")
            @RequestParam(value = "includeInactive", required = false)
					boolean includeInactive
	) {
		List<TourDto> userTours = requestHandler.getAllToursForUser(userContext, includeInactive);

		return ResponseEntity.ok(userTours);
	}

	@ApiOperation(
			value = "Creates a new tour."
	)
	@ApiResponses({
			@ApiResponse(code = 200, message = "Success. The tour has been successfully created."),
			@ApiResponse(code = 500, message = "Failed. Something went wrong on our side."),
	})
	@PostMapping
	public ResponseEntity<Resource<HATEOASPlaceholder>> createTour(
			@ApiParam("The JSON body of the request. Contains parameters of the tour.")
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

	@ApiOperation(
			value = "Updates an existing tour."
	)
	@ApiResponses({
			@ApiResponse(code = 200, message = "Success. The tour has been successfully updated."),
			@ApiResponse(code = 404, message = "Failed. The tour does not exist yet."),
			@ApiResponse(code = 500, message = "Failed. Something went wrong on our side."),
	})
	@PutMapping("/{tourId}")
	@CachePut(TOURS_CACHE_NAME)
	public ResponseEntity updateTour(
			@ApiParam("The id of the tour to update.")
			@PathVariable("tourId")
			@NotNull
					Long tourId,

			@ApiParam("The JSON body of the request. Contains parameters of the tour.")
			@RequestBody
			@Valid
			@NotNull
					TourDto tourDto
	) {
		requestHandler.updateTour(tourId, tourDto, userContext);

		return ResponseEntity.ok().build();
	}

	@ApiOperation(
			value = "Deactivates an existing tour.\n" +
					"Tours can't be deleted as **Expenses** would then lose their tour."
	)
	@ApiResponses({
			@ApiResponse(code = 200, message = "Success. The tour has been successfully deactivated."),
			@ApiResponse(code = 404, message = "Failed. The tour does not exist yet."),
			@ApiResponse(code = 500, message = "Failed. Something went wrong on our side."),
	})
	@DeleteMapping("/{tourId}")
	@CachePut(TOURS_CACHE_NAME)
	public ResponseEntity deleteTour(
			@PathVariable("tourId")
			@NotNull
					Long tourId
	) {
		requestHandler.deleteTour(tourId, userContext);

		return ResponseEntity.ok().build();
	}

	@ApiOperation(
			value = "Activates an existing tour."
	)
	@ApiResponses({
			@ApiResponse(code = 200, message = "Success. The tour has been successfully activated."),
			@ApiResponse(code = 404, message = "Failed. The tour does not exist yet."),
			@ApiResponse(code = 500, message = "Failed. Something went wrong on our side."),
	})
	@PutMapping("{tourId}/activate")
	@CachePut(TOURS_CACHE_NAME)
	public ResponseEntity activateTour(
			@PathVariable("tourId")
			@NotNull
					Long tourId
	) {
		requestHandler.activateTour(tourId, userContext);

		return ResponseEntity.ok().build();
	}
}
