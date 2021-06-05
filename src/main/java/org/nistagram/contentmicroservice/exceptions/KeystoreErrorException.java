package org.nistagram.contentmicroservice.exceptions;

public class KeystoreErrorException extends RuntimeException {
    public KeystoreErrorException() {
        super("There's been an error with keystore.");
    }
}
