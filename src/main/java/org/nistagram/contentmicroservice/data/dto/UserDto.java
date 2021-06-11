package org.nistagram.contentmicroservice.data.dto;

import org.nistagram.contentmicroservice.util.Constants;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class UserDto implements Serializable {
    @NotNull
    @Pattern(regexp = Constants.USERNAME_PATTERN, message = Constants.USERNAME_INVALID_MESSAGE)
    private String username;
    private boolean profilePrivate = false;

    public UserDto() {
    }

    public UserDto(String username, boolean profilePrivate) {
        this.username = username;
        this.profilePrivate = profilePrivate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isProfilePrivate() {
        return profilePrivate;
    }

    public void setProfilePrivate(boolean profilePrivate) {
        this.profilePrivate = profilePrivate;
    }
}
