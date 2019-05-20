package com.sharepool.server.rest.tour.validation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = LocationsNotEqualValidator.class)
public @interface LocationsNotEqual {

	String message() default "The from and to location must not be the same";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}


