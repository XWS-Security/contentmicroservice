package org.nistagram.contentmicroservice.logging;

public interface LoggerService {
    void logException(String message);

    void logValidationFailed(String message);

    void logTokenException(String message);

    void logObjectNotFoundException(String message);

    void logPostSaveToFavourites(Long postId);

    void logPostSaveToFavouritesSuccess(Long postId);

    void logPostSaveToFavouritesFailed(Long postId, String reason);

    void logPostRemoveFromFavourites(Long postId);

    void logPostRemoveFromFavouritesSuccess(Long postId);

    void logPostRemoveFromFavouritesFailed(Long postId, String reason);

    void logCheckingIfPostIsFavourite(Long postId);

    void logCheckingIfPostIsFavouriteSuccess(Long postId);

    void logCheckingIfPostIsFavouriteFailed(Long postId, String reason);

    void logGetImage(String imageName);

    void logGetImageSuccess(String imageName);

    void logGetImageFailed(String imageName, String reason);

    void logAddingToCloseFriend(String username, String friendUsername);

    void logAddingToCloseFriendSuccess(String userName, String friendUsername);

    void logAddingToCloseFriendFailed(String friendUsername, String reason);

    void logRemovingCloseFriend(String username, String friendUsername);

    void logRemovingCloseFriendSuccess(String userName, String friendUsername);

    void logRemovingCloseFriendFailed(String friendUsername, String reason);

    void logComment(Long postId, String username);

    void logCommentSuccess(Long postId, String username);

    void logCommentFailed(Long postId, String reason);

    void logLike(String userName, Long PostId);

    void logLikeSuccess(String userName, Long PostId);

    void logLikeFailed(Long PostId, String reason);

    void logDislike(String username, Long PostId);

    void logDislikeSuccess(String userName, Long PostId);

    void logDislikeFailed(Long PostId, String reason);

    void logCreateUser(String username);

    void logCreateUserSuccess(String userName);

    void logCreateUserFailed(String userName, String reason);

    void logUpdateUser(String username);

    void logUpdateUserSuccess(String userName);

    void logUpdateUserFailed(String userName, String reason);

    void logUploadStory(String username);

    void logUploadStorySuccess(String userName);

    void logUploadStoryFailed(String reason);

    void logUploadPost(String username);

    void logUploadPostSuccess(String userName);

    void logUploadPostFailed(String userName, String reason);

    void getStories(String username);

    void getStoriesSuccess(String username);

    void getStoriesFailed(String reason);
}
