package org.nistagram.contentmicroservice.data.dto;

import javax.validation.constraints.Pattern;

public class FollowRequestAccessResponseDto {
    private boolean accessAllowed;

    @Pattern(regexp = "^[^<>]")
    private String message;

    public FollowRequestAccessResponseDto() {
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
