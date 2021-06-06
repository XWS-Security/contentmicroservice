package org.nistagram.contentmicroservice.exceptions;

public class UserAlreadyRemoved extends RuntimeException{
    public UserAlreadyRemoved(){
        super("User is already removed");
    }
    public UserAlreadyRemoved(String username){
        super("User " + username + " is already removed");
    }

}
