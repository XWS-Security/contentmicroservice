package org.nistagram.contentmicroservice.data.dto;

import java.io.Serializable;
import java.util.Objects;

public class ProfileImageDto implements Serializable {
    private String imageName;
    private Long postId;

    public ProfileImageDto(String imageName, Long postId) {
        this.imageName = imageName;
        this.postId = postId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfileImageDto that = (ProfileImageDto) o;
        return imageName.equals(that.imageName) &&
                postId.equals(that.postId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imageName, postId);
    }
}
