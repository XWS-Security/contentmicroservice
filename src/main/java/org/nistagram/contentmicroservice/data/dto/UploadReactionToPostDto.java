package org.nistagram.contentmicroservice.data.dto;

import javax.validation.constraints.Min;
import java.io.Serializable;

public class UploadReactionToPostDto implements Serializable {
    @Min(1L)
    private long postId;

    public UploadReactionToPostDto() {
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }
}
