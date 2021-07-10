package org.nistagram.contentmicroservice.service;

import org.nistagram.contentmicroservice.data.dto.ContentMessageInfoDto;
import org.nistagram.contentmicroservice.data.dto.LikesDislikesCommentsDto;

import javax.net.ssl.SSLException;

public interface ContentService {
    ContentMessageInfoDto getContentInfo(long id, String token) throws SSLException;

    LikesDislikesCommentsDto getLikesDislikesAndComments(Long id);
}
