package com.sharepool.server.rest.user.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Size(min = 3, max = 20)
@Constraint(validatedBy = {})
@Retention(RetentionPolicy.RUNTIME)
public @interface FirstName {

    String message() default "Invalid Password";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
