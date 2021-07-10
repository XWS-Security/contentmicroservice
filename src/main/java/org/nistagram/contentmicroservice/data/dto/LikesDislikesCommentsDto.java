package org.nistagram.contentmicroservice.data.dto;

import java.io.Serializable;

public class LikesDislikesCommentsDto implements Serializable {

    private Integer likes;
    private Integer dislikes;
    private Integer comments;

    public LikesDislikesCommentsDto() {
    }

    public LikesDislikesCommentsDto(Integer likes, Integer dislikes, Integer comments) {
        this.likes = likes;
        this.dislikes = dislikes;
        this.comments = comments;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getDislikes() {
        return dislikes;
    }

    public void setDislikes(Integer dislikes) {
        this.dislikes = dislikes;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }
}
