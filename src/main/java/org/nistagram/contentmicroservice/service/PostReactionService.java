package org.nistagram.contentmicroservice.service;

import org.nistagram.contentmicroservice.data.dto.UploadCommentDto;

public interface PostReactionService {
    void comment(UploadCommentDto dto);

    void like(long postId);

    void dislike(long postId);
}
