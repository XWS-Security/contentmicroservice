package org.nistagram.contentmicroservice.security.customvalidators;

import org.nistagram.contentmicroservice.util.Constants;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class TaggedUserValidator implements ConstraintValidator<TaggedUserConstraint, List<String>> {
    @Override
    public boolean isValid(List<String> strings, ConstraintValidatorContext constraintValidatorContext) {
        for (String username : strings
        ) {
            if (!username.matches(Constants.USERNAME_PATTERN)) {
                return false;
            }
        }
        return true;
    }
}
