package org.nistagram.contentmicroservice.data.dto;

import org.nistagram.contentmicroservice.security.customvalidators.TagsValidator;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Pattern;
import java.util.List;

public class CreateStoryDto {

    @Pattern(regexp = "^[^<>]+", message = "Invalid character!")
    private String location;

    @TagsValidator
    private List<String> tags;

    private boolean closeFriends;

    private List<MultipartFile> files;

    private boolean highlights;

    public CreateStoryDto() {
    }

    public CreateStoryDto(String location, List<String> tags, boolean closeFriends, List<MultipartFile> files, boolean highlights) {
        this.location = location;
        this.tags = tags;
        this.closeFriends = closeFriends;
        this.files = files;
        this.highlights = highlights;
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

    public boolean isHighlights() {
        return highlights;
    }

    public void setHighlights(boolean highlights) {
        this.highlights = highlights;
    }
}
