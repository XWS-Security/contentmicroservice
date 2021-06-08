package org.nistagram.contentmicroservice.exceptions;

public class AccessToUserProfileDeniedException extends RuntimeException {
    public AccessToUserProfileDeniedException(String message) {
        super(message);
    }
}
