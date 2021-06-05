package org.nistagram.contentmicroservice.data.dto;

import java.io.Serializable;
import java.util.List;

public class ProfileInfoDto implements Serializable {
    private List<ProfileImageDto> images;
    private String about;
    private String profilePictureName;
    private boolean hasStories;

    public ProfileInfoDto(List<ProfileImageDto> images, String about, String profilePictureName, boolean hasStories) {
        this.images = images;
        this.about = about;
        this.profilePictureName = profilePictureName;
        this.hasStories = hasStories;
    }

    public ProfileInfoDto() {
    }

    public boolean isHasStories() {
        return hasStories;
    }

    public void setHasStories(boolean hasStories) {
        this.hasStories = hasStories;
    }

    public List<ProfileImageDto> getImages() {
        return images;
    }

    public void setImages(List<ProfileImageDto> images) {
        this.images = images;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getProfilePictureName() {
        return profilePictureName;
    }

    public void setProfilePictureName(String profilePictureName) {
        this.profilePictureName = profilePictureName;
    }
}
