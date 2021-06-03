package org.nistagram.contentmicroservice.data.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PostDto implements Serializable {
    private LocationDto location;
    private List<String> tags;
    private Date date;
    private int likes;
    private int dislikes;
    private List<Long> commentIds;
    private String about;

    public PostDto(LocationDto location, List<String> tags, Date date, int likes, int dislikes, List<Long> commentIds, String about) {
        this.location = location;
        this.tags = tags;
        this.date = date;
        this.likes = likes;
        this.dislikes = dislikes;
        this.commentIds = commentIds;
        this.about = about;
    }

    public PostDto() {
    }

    public LocationDto getLocation() {
        return location;
    }

    public void setLocation(LocationDto location) {
        this.location = location;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public List<Long> getCommentIds() {
        return commentIds;
    }

    public void setCommentIds(List<Long> commentIds) {
        this.commentIds = commentIds;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
