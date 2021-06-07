package org.nistagram.contentmicroservice.exceptions;

public class PostDoesNotExistException extends RuntimeException {
    public PostDoesNotExistException(long postId) {
        super("Post with id: " + postId + " does not exist.");
    }
}
