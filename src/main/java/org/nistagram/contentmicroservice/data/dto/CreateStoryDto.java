package org.nistagram.contentmicroservice.data.dto;

import org.nistagram.contentmicroservice.security.customvalidators.TaggedUserConstraint;
import org.nistagram.contentmicroservice.security.customvalidators.TagsConstraint;
import org.nistagram.contentmicroservice.util.Constants;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Pattern;
import java.util.List;

public class CreateStoryDto {

    @Pattern(regexp = Constants.PLAIN_TEXT_PATTERN, message = Constants.INVALID_CHARACTER_MESSAGE)
    private String location;

    @TagsConstraint
    private List<String> tags;

    private boolean closeFriends;

    private List<MultipartFile> files;

    private boolean highlights;

    @TaggedUserConstraint
    private List<String> taggedUsers;

    public CreateStoryDto() {
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

    public List<String> getTaggedUsers() {
        return taggedUsers;
    }

    public void setTaggedUsers(List<String> taggedUsers) {
        this.taggedUsers = taggedUsers;
    }
}
