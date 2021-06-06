package org.nistagram.contentmicroservice.service;

import org.nistagram.contentmicroservice.data.dto.StoryDto;

import java.util.List;

public interface IStoryService {
    List<StoryDto> getActiveUserStories(String username);

    List<StoryDto> getHighlights(String username);
}
