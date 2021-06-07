package org.nistagram.contentmicroservice.data.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class UploadCommentDto implements Serializable {
    @NotNull
    @Pattern(regexp = "^[^<>]+", message = "Invalid character")
    private String text;
    @Min(1L)
    private long postId;

    public UploadCommentDto() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }
}
