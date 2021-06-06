package org.nistagram.contentmicroservice.data.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class CreateStoryDto {

    private String location;
    private List<String> tags;
    private boolean closeFriends;
    private List<MultipartFile> files;

    public CreateStoryDto() {
    }

    public CreateStoryDto(String location, List<String> tags, boolean closeFriends, List<MultipartFile> files) {
        this.location = location;
        this.tags = tags;
        this.closeFriends = closeFriends;
        this.files = files;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public boolean isCloseFriends() {
        return closeFriends;
    }

    public void setCloseFriends(boolean closeFriends) {
        this.closeFriends = closeFriends;
    }

    public List<MultipartFile> getFiles() {
        return files;
    }

    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }
}
