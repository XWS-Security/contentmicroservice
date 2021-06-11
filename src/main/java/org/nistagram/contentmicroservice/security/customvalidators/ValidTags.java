package org.nistagram.contentmicroservice.security.customvalidators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ValidTags implements ConstraintValidator<TagsValidator, List<String>> {
    @Override
    public boolean isValid(List<String> tags, ConstraintValidatorContext constraintValidatorContext) {
        for (String tag : tags) {
            if (tag.contains("<") || tag.contains(">")) {
                return false;
            }
        }
        return true;
    }
}
