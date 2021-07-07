package org.nistagram.contentmicroservice.data.dto;

import java.io.Serializable;

public class ContentMessageInfoDto implements Serializable {
    private String profileImage;
    private String username;
    private String type;
    private boolean hasAccess;
    private String contentPath;

    public ContentMessageInfoDto() {
    }

    public ContentMessageInfoDto(String profileImage, String username, String type, boolean hasAccess, String contentPath) {
        this.profileImage = profileImage;
        this.username = username;
        this.type = type;
        this.hasAccess = hasAccess;
        this.contentPath = contentPath;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isHasAccess() {
        return hasAccess;
    }

    public void setHasAccess(boolean hasAccess) {
        this.hasAccess = hasAccess;
    }

    public String getContentPath() {
        return contentPath;
    }

    public void setContentPath(String contentPath) {
        this.contentPath = contentPath;
    }
}
