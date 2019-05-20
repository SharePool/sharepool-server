package com.sharepool.server.rest.tour.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.sharepool.server.rest.tour.dto.TourCreationDto;

public class LocationsNotEqualValidator implements ConstraintValidator<LocationsNotEqual, TourCreationDto> {

	private LocationsNotEqual constraint;

	public void initialize(LocationsNotEqual constraint) {
		this.constraint = constraint;
	}

	public boolean isValid(TourCreationDto tourCreationDto, ConstraintValidatorContext context) {
		if (tourCreationDto == null
				|| tourCreationDto.getFrom() == null
				|| tourCreationDto.getTo() == null) {

			return true;
		}

		if (tourCreationDto.getFrom().equals(tourCreationDto.getTo())) {
			context.disableDefaultConstraintViolation();

			context.buildConstraintViolationWithTemplate(constraint.message())
					.addPropertyNode("from")
					.addConstraintViolation();

			return false;
		}

		return true;
	}
}
