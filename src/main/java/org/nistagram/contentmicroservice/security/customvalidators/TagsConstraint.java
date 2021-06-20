package org.nistagram.contentmicroservice.security.customvalidators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TagsValidator.class)
public @interface TagsConstraint {
    String message() default "Greater or less signs are not allowed!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
