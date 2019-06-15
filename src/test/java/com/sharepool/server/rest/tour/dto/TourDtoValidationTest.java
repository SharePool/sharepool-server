package com.sharepool.server.rest.tour.dto;

import com.sharepool.server.common.dto.BaseDtoValidationTest;
import org.junit.Test;

public class TourDtoValidationTest extends BaseDtoValidationTest {

	@Test
	public void testNullFields() {
		TourDto TourDto = new TourDto();
		TourDto.setCost(1);

		assertContainsViolation(TourDto, "from", 3);
		assertContainsViolation(TourDto, "to");
		assertContainsViolation(TourDto, "currency");
	}

	@Test
	public void testCurrencyValidation() {
		TourDto TourDto = createValidTourDto();
		TourDto.setCurrency("X");

		assertContainsViolation(TourDto, "currency", 1);
	}

	@Test
	public void testLocationsNotEqualValidation() {
		TourDto TourDto = createValidTourDto();
		TourDto.setFrom("Linz");
		TourDto.setTo("Linz");

		assertContainsViolation(TourDto, "from", 1);
	}
}