package com.sharepool.server.rest.tour.dto;

import com.sharepool.server.common.dto.BaseDtoValidationTest;
import org.junit.Test;

public class TourDtoValidationTest extends BaseDtoValidationTest {

	@Test
	public void testNullFields() {
		TourDto TourDto = new TourDto();
		TourDto.setCost(1);

		assertContainsViolation(TourDto, "from", 4);
		assertContainsViolation(TourDto, "to");
		assertContainsViolation(TourDto, "currency");
		assertContainsViolation(TourDto, "ownerId");
	}

	@Test
	public void testCurrencyValidation() {
		TourDto TourDto = createValidDto();
		TourDto.setCurrency("X");

		assertContainsViolation(TourDto, "currency", 1);
	}

	private TourDto createValidDto() {
		TourDto TourDto = new TourDto();

		TourDto.setFrom("Linz");
		TourDto.setTo("Hagenberg");
		TourDto.setCurrency("EUR");
		TourDto.setCost(1);
		TourDto.setKilometers(30);
		TourDto.setOwnerId(1L);

		return TourDto;
	}

	@Test
	public void testLocationsNotEqualValidation() {
		TourDto TourDto = createValidDto();
		TourDto.setFrom("Linz");
		TourDto.setTo("Linz");

		assertContainsViolation(TourDto, "from", 1);
	}
}