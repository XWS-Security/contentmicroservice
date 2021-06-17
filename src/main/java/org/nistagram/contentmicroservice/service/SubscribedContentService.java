package org.nistagram.contentmicroservice.service;

import org.nistagram.contentmicroservice.data.dto.HomePageImageDto;

import java.util.List;

public interface SubscribedContentService {
    List<HomePageImageDto> getLatestContent();
}
