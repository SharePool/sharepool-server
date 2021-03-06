package com.sharepool.server.common.dto;

import com.sharepool.server.common.AbstractUtilTest;
import org.junit.Assert;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class BaseDtoValidationTest extends AbstractUtilTest {

	private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

	protected <T> void assertContainsViolation(T object, String propertyPath) {
		assertContainsViolation(object, propertyPath, null);
	}

	protected <T> void assertContainsViolation(T object, String propertyPath, Integer violationCount) {
		Set<ConstraintViolation<T>> violations = validator.validate(object);

		if (violationCount != null) {
			Assert.assertEquals(violationCount.intValue(), violations.size());
		}

		Assert.assertTrue(violations.stream()
				.anyMatch(v -> v.getPropertyPath().toString().equals(propertyPath)));
	}
}
