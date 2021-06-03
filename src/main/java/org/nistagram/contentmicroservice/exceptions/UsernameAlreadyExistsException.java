package org.nistagram.contentmicroservice.exceptions;

public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException() {
        super("Username is taken, please choose another one.");
    }
}
