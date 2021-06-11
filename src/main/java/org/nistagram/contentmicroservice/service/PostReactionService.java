package org.nistagram.contentmicroservice.service;

import org.nistagram.contentmicroservice.data.dto.UploadCommentDto;

import javax.net.ssl.SSLException;

public interface PostReactionService {
    void comment(UploadCommentDto dto, String token) throws SSLException, InterruptedException;

    void like(long postId, String token) throws SSLException, InterruptedException;

    void dislike(long postId, String token) throws SSLException, InterruptedException;
}
