package com.sharepool.server.rest.tour;

import com.sharepool.server.logic.tour.TourRestRequestHandler;
import com.sharepool.server.rest.tour.dto.TourDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("tours")
public class TourResource {

	private final TourRestRequestHandler requestHandler;

	public TourResource(TourRestRequestHandler requestHandler) {this.requestHandler = requestHandler;}

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
