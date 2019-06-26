package com.sharepool.server.rest.tour.validation;

import com.sharepool.server.rest.tour.dto.TourDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LocationsNotEqualValidator implements ConstraintValidator<LocationsNotEqual, TourDto> {

	private LocationsNotEqual constraint;

	public void initialize(LocationsNotEqual constraint) {
		this.constraint = constraint;
	}

    public boolean isValid(TourDto tourDto, ConstraintValidatorContext context) {
        if (tourDto == null
                || tourDto.getFrom() == null
                || tourDto.getTo() == null) {

			return true;
		}

        if (tourDto.getFrom().equals(tourDto.getTo())) {
			context.disableDefaultConstraintViolation();

			context.buildConstraintViolationWithTemplate(constraint.message())
					.addPropertyNode("from")
					.addConstraintViolation();

			return false;
		}

		return true;
	}
}
