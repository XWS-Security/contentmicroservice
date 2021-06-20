package org.nistagram.contentmicroservice.security.customvalidators;

import org.nistagram.contentmicroservice.util.Constants;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TaggedUserValidator.class)
public @interface TaggedUserConstraint {
    String message() default Constants.USERNAME_INVALID_MESSAGE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
