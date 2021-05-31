package org.nistagram.contentmicroservice.data.dto;

public class ProfileImageDto {
    private String imagePath;
    private Long postId;

    public ProfileImageDto(String imagePath, Long postId) {
        this.imagePath = imagePath;
        this.postId = postId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
