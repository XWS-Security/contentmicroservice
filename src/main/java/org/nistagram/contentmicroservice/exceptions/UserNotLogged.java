package org.nistagram.contentmicroservice.exceptions;

public class UserNotLogged extends RuntimeException{
    public UserNotLogged(){
        super("Session has expired. User is not logged in.");
    }
}
