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

	@Test
	public void testCurrencyValidation() {
		TourCreationDto tourCreationDto = createValidDto();
		tourCreationDto.setCurrency("X");

		assertContainsViolation(tourCreationDto, "currency", 1);
	}

	private TourCreationDto createValidDto() {
		TourCreationDto tourCreationDto = new TourCreationDto();

		tourCreationDto.setFrom("Linz");
		tourCreationDto.setTo("Hagenberg");
		tourCreationDto.setCurrency("EUR");
		tourCreationDto.setCost(1);
		tourCreationDto.setKilometers(30);
		tourCreationDto.setOwnerId(1L);

		return tourCreationDto;
	}

	@Test
	public void testLocationsNotEqualValidation() {
		TourCreationDto tourCreationDto = createValidDto();
		tourCreationDto.setFrom("Linz");
		tourCreationDto.setTo("Linz");

		assertContainsViolation(tourCreationDto, "from", 1);
	}
}