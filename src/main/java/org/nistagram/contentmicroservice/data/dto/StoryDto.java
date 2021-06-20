package org.nistagram.contentmicroservice.data.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class StoryDto implements Serializable {

    private Long id;
    private String path;
    private LocationDto location;
    private List<String> tags;
    private Date date;
    private List<String> taggedUsers;

    public StoryDto() {
    }

    public StoryDto(Long id, String path, LocationDto location, List<String> tags, Date date, List<String> taggedUsers) {
        this.id = id;
        this.path = path;
        this.location = location;
        this.tags = tags;
        this.date = date;
        this.taggedUsers = taggedUsers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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

    public List<String> getTaggedUsers() {
        return taggedUsers;
    }

    public void setTaggedUsers(List<String> taggedUsers) {
        this.taggedUsers = taggedUsers;
    }
}
