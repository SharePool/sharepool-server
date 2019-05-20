package com.sharepool.server.rest.tour.validation;

import java.util.Currency;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsValidCurrencyValidator implements ConstraintValidator<IsValidCurrency, String> {
	public void initialize(IsValidCurrency constraint) {
	}

	public boolean isValid(String currencyCode, ConstraintValidatorContext context) {
		if (currencyCode == null) {
			return true;
		}

		try {
			Currency.getInstance(currencyCode);
		} catch (IllegalArgumentException e) {
			return false;
		}

		return true;
	}
}
