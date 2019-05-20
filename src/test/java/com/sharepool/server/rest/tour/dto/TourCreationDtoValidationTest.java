package com.sharepool.server.rest.tour.dto;

import org.junit.Test;

import com.sharepool.server.common.dto.BaseDtoValidationTest;

public class TourCreationDtoValidationTest extends BaseDtoValidationTest {

	@Test
	public void testNullFields() {
		TourCreationDto tourCreationDto = new TourCreationDto();
		tourCreationDto.setCost(1);

		assertContainsViolation(tourCreationDto, "from", 4);
		assertContainsViolation(tourCreationDto, "to");
		assertContainsViolation(tourCreationDto, "currency");
		assertContainsViolation(tourCreationDto, "ownerId");
	}
}