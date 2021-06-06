package org.nistagram.contentmicroservice.exceptions;

public class UserAlreadyAdded extends RuntimeException{

    public UserAlreadyAdded(){
        super("User is already added");
    }
    public UserAlreadyAdded(String username){
        super("User " + username + " is already added");
    }

}
