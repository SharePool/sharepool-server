package com.sharepool.server.rest.tour.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = IsValidCurrencyValidator.class)
public @interface IsValidCurrency {

	String message() default "The currency code must be a valid ISO 4217 code";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
