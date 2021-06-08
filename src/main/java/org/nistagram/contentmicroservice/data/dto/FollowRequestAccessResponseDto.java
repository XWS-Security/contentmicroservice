package org.nistagram.contentmicroservice.data.dto;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class FollowRequestAccessResponseDto implements Serializable {
    private boolean accessAllowed;

    @Pattern(regexp = "^[^<>]")
    private String message;

    public FollowRequestAccessResponseDto() {
    }

    public FollowRequestAccessResponseDto(boolean accessAllowed, @Pattern(regexp = "^[^<>]") String message) {
        this.accessAllowed = accessAllowed;
        this.message = message;
    }

    public boolean isAccessAllowed() {
        return accessAllowed;
    }

    public void setAccessAllowed(boolean accessAllowed) {
        this.accessAllowed = accessAllowed;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
