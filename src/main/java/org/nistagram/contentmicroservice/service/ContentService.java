package org.nistagram.contentmicroservice.service;

import org.nistagram.contentmicroservice.data.dto.ContentMessageInfoDto;

import javax.net.ssl.SSLException;

public interface ContentService {
    ContentMessageInfoDto getContentInfo(long id, String token) throws SSLException;
}
