package org.nistagram.contentmicroservice.exceptions;

public class NotFoundException extends RuntimeException{
    public NotFoundException(Long id){
        super("Object with id: " + id.toString() + "could not be found.");
    }

    public NotFoundException(String id){
        super("Object with id: " + id + "could not be found.");
    }

    public NotFoundException(){
        super();
    }
}
