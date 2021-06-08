package org.nistagram.contentmicroservice.service;

import org.nistagram.contentmicroservice.data.dto.UploadCommentDto;

import javax.net.ssl.SSLException;

public interface PostReactionService {
    void comment(UploadCommentDto dto) throws SSLException;

    void like(long postId) throws SSLException;

    void dislike(long postId) throws SSLException;
}
