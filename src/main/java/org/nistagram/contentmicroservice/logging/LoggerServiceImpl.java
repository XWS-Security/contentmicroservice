package org.nistagram.contentmicroservice.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerServiceImpl implements LoggerService {
    private final Logger logger;

    public LoggerServiceImpl(Class<?> parentClass) {
        this.logger = LoggerFactory.getLogger(parentClass);
    }

    @Override
    public void logException(String message) {
        logger.error("Unexpected exception: { message: {} }", message);
    }

    @Override
    public void logValidationFailed(String message) {
        logger.error("Validation failed: { message: {} }", message);
    }

    @Override
    public void logTokenException(String message) {
        logger.error("Token issue: {message: {} }", message);
    }

    @Override
    public void logObjectNotFoundException(String message) {
        logger.error("Object couldn't be found issue: {message: {} }", message);
    }

    @Override
    public void logPostSaveToFavourites(Long postId) {
        logger.info("Saving post: {'postId' {}}", postId.toString());
    }

    @Override
    public void logPostSaveToFavouritesSuccess(Long postId) {
        logger.info("Post saved successfully: {'postId': {} }", postId.toString());
    }

    @Override
    public void logPostSaveToFavouritesFailed(Long postId, String reason) {
        logger.error("Saving post failed: { 'postId': {}, 'reason': {} }", postId.toString(), reason);
    }

    @Override
    public void logPostRemoveFromFavourites(Long postId) {
        logger.info("Removing post: {'postId' {}}", postId.toString());
    }

    @Override
    public void logPostRemoveFromFavouritesSuccess(Long postId) {
        logger.info("Post removed successfully: {'postId': {} }", postId.toString());
    }

    @Override
    public void logPostRemoveFromFavouritesFailed(Long postId, String reason) {
        logger.error("Removing post failed: { 'postId': {}, 'reason': {} }", postId.toString(), reason);
    }

    @Override
    public void logCheckingIfPostIsFavourite(Long postId) {
        logger.info("Checking if post exists: {'postId' {}}", postId.toString());
    }

    @Override
    public void logCheckingIfPostIsFavouriteSuccess(Long postId) {
        logger.info("Checking if post exists succeeded: {'postId': {} }", postId.toString());
    }

    @Override
    public void logCheckingIfPostIsFavouriteFailed(Long postId, String reason) {
        logger.error("Checking if post exists failed: { 'postId': {}, 'reason': {} }", postId.toString(), reason);
    }

    @Override
    public void logGetImage(String imageName) {
        logger.info("Getting image: {'imageName' {}}", imageName);
    }

    @Override
    public void logGetImageSuccess(String imageName) {
        logger.info("Getting image succeeded: {'imageName': {} }", imageName);
    }

    @Override
    public void logGetImageFailed(String imageName, String reason) {
        logger.error("Getting image failed: { 'Image name': {}, 'reason': {} }", imageName, reason);
    }

    @Override
    public void logAddingToCloseFriend(String username, String friendUsername) {
        logger.info("Adding close friend: {'username':{}}, {'friendUsername':{}}", username, friendUsername);
    }

    @Override
    public void logAddingToCloseFriendSuccess(String userName, String friendUsername) {
        logger.info("Adding close friend succeeded: {'username':{}}, {'friendUsername':{}}", userName, friendUsername);
    }


    @Override
    public void logAddingToCloseFriendFailed(String friendUsername, String reason) {
        logger.error("Adding close friend failed: {'friendUsername':{}}, {'reason':{}}", friendUsername, reason);
    }

    @Override
    public void logRemovingCloseFriend(String username, String friendUsername) {
        logger.info("Removing close friend: {'username':{}}, {'friendUsername':{}}", username, friendUsername);
    }

    @Override
    public void logRemovingCloseFriendSuccess(String userName, String friendUsername) {
        logger.info("Removing close friend succeeded: {'username':{}}, {'friendUsername':{}}", userName, friendUsername);
    }

    @Override
    public void logRemovingCloseFriendFailed(String friendUsername, String reason) {
        logger.error("Adding close friend failed: {'friendUsername':{}}, {'reason':{}}", friendUsername, reason);
    }

    @Override
    public void logComment(Long postId, String username) {
        logger.info("Commenting: {'postId':{}}, {'username':{}}", postId.toString(),username);
    }

    @Override
    public void logCommentSuccess(Long postId, String username) {
        logger.info("Commenting: {'postId':{}}, {'username':{}}", postId.toString(),username);
    }

    @Override
    public void logCommentFailed(Long postId, String reason) {
        logger.error("Commenting: {'postId':{}}, {'reason':{}}", postId.toString(), reason);
    }

    @Override
    public void logLike(String username, Long postId) {
        logger.info("Liking: {'postId':{}}, {'username':{}}", postId.toString(),username);
    }

    @Override
    public void logLikeSuccess(String username, Long postId) {
        logger.info("Liking succeeded: {'postId':{}}, {'username':{}}", postId.toString(),username);
    }

    @Override
    public void logLikeFailed(Long postId, String reason) {
        logger.error("Liking failed: {'postId':{}}, {'reason':{}}", postId.toString(), reason);
    }
    @Override
    public void logDislike(String username, Long postId)  {
        logger.info("Disliking: {'postId':{}}, {'username':{}}", postId.toString(),username);
    }

    @Override
    public void logDislikeSuccess(String username, Long postId) {
        logger.info("Disliking succeeded: {'postId':{}}, {'username':{}}", postId.toString(),username);
    }

    @Override
    public void logDislikeFailed(Long postId, String reason) {
        logger.error("Disliking failed: {'postId':{}}, {'reason':{}}", postId.toString(), reason);
    }

    @Override
    public void logCreateUser(String username) {
        logger.info("Creating user: {'username':{}}",username);
    }

    @Override
    public void logCreateUserSuccess(String username) {
        logger.info("Creating user succeeded: {'username':{}}",username);
    }

    @Override
    public void logCreateUserFailed(String username, String reason) {
        logger.error("Creating user failed: {'username':{}}, {'reason':{}}",username, reason);
    }

    @Override
    public void logUpdateUser(String username) {
        logger.info("Updating user: {'username':{}}",username);
    }

    @Override
    public void logUpdateUserSuccess(String username) {
        logger.info("Updating user succeeded: {'username':{}}",username);
    }

    @Override
    public void logUpdateUserFailed(String username, String reason) {
        logger.error("Creating user failed: {'username':{}}, {'reason':{}}",username, reason);
    }

    @Override
    public void logUploadStory(String username) {
        logger.info("Uploading story: {'username':{}}",username);
    }

    @Override
    public void logUploadStorySuccess(String username) {
        logger.info("Uploading story succeeded: {'username':{}}",username);
    }

    @Override
    public void logUploadStoryFailed(String reason) {
        logger.error("Uploading story failed: {'reason':{}}", reason);
    }

    @Override
    public void logUploadPost(String username) {
        logger.info("Uploading post: {'username':{}}",username);
    }

    @Override
    public void logUploadPostSuccess(String username) {
        logger.info("Uploading post succeeded: {'username':{}}",username);
    }

    @Override
    public void logUploadPostFailed(String username, String reason) {
        logger.error("Uploading post failed: {'username':{}}, {'reason':{}}",username, reason);
    }

    @Override
    public void getStories(String username) {
        logger.info("Getting stories: {'username':{}}",username);
    }

    @Override
    public void getStoriesSuccess(String username) {
        logger.info("Getting stories succeeded: {'username':{}}",username);
    }

    @Override
    public void getStoriesFailed(String reason) {
        logger.error("Getting stories failed: {'reason':{}}", reason);
    }


}
