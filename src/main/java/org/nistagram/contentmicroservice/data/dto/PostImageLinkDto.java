package org.nistagram.contentmicroservice.data.dto;

public class PostImageLinkDto {
    private String imageName;
    private Long id;
    private String userId;
    private String userProfileImageName;

    public PostImageLinkDto(String imageName, Long id, String userId, String userProfileImageName) {
        this.imageName = imageName;
        this.id = id;
        this.userId = userId;
        this.userProfileImageName = userProfileImageName;
    }

    public PostImageLinkDto() {
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserProfileImageName() {
        return userProfileImageName;
    }

    public void setUserProfileImageName(String userProfileImageName) {
        this.userProfileImageName = userProfileImageName;
    }
}
