package org.nistagram.contentmicroservice.service;

import org.nistagram.contentmicroservice.data.dto.CreateStoryDto;
import org.nistagram.contentmicroservice.data.dto.StoryDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IStoryService {
    List<StoryDto> getActiveUserStories(String username);

    void createStory(CreateStoryDto storyDto, List<MultipartFile> files) throws IOException;

    List<StoryDto> getHighlights(String username);
}
