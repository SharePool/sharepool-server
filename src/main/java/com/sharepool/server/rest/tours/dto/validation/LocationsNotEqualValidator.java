package com.sharepool.server.rest.tours.dto.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.sharepool.server.rest.tours.dto.TourCreationDto;

public class LocationsNotEqualValidator implements ConstraintValidator<LocationsNotEqual, TourCreationDto> {
	public void initialize(LocationsNotEqual constraint) {
	}

	public boolean isValid(TourCreationDto tourCreationDto, ConstraintValidatorContext context) {
		if (tourCreationDto == null
				|| tourCreationDto.getFrom() == null
				|| tourCreationDto.getTo() == null) {

			return true;
		}

		if (tourCreationDto.getFrom().equals(tourCreationDto.getTo())) {
			return false;
		}

		return true;
	}
}
