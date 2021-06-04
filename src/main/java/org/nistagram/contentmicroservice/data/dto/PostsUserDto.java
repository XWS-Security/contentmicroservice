package org.nistagram.contentmicroservice.data.dto;

import java.io.Serializable;

public class PostsUserDto implements Serializable {

    private String username;
    private String profileImg;

    public PostsUserDto(String username, String profileImg) {
        this.username = username;
        this.profileImg = profileImg;
    }

    public PostsUserDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }
}
