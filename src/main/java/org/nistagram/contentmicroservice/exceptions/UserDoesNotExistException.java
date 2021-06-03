package org.nistagram.contentmicroservice.exceptions;

public class UserDoesNotExistException extends RuntimeException {
    public UserDoesNotExistException() {
        super("User with selected username does not exist.");
    }
}
