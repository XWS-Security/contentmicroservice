package org.nistagram.contentmicroservice.data.dto;

import java.io.Serializable;

public class HomePageImageDto implements Serializable {
    private String username;
    private String profileImage;
    private Long postId;

    public HomePageImageDto() {
    }

    public HomePageImageDto(String username, String profileImage, Long postId) {
        this.username = username;
        this.profileImage = profileImage;
        this.postId = postId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
