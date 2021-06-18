package org.nistagram.contentmicroservice.service.impl;

import org.nistagram.contentmicroservice.data.dto.CreateStoryDto;
import org.nistagram.contentmicroservice.data.dto.LocationDto;
import org.nistagram.contentmicroservice.data.dto.StoryDto;
import org.nistagram.contentmicroservice.data.model.NistagramUser;
import org.nistagram.contentmicroservice.data.model.content.Content;
import org.nistagram.contentmicroservice.data.model.content.Story;
import org.nistagram.contentmicroservice.data.repository.ContentRepository;
import org.nistagram.contentmicroservice.data.repository.LocationRepository;
import org.nistagram.contentmicroservice.data.repository.StoryRepository;
import org.nistagram.contentmicroservice.data.repository.NistagramUserRepository;
import org.nistagram.contentmicroservice.exceptions.UserNotLogged;
import org.nistagram.contentmicroservice.service.IStoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class StoryServiceImpl implements IStoryService {

    private final NistagramUserRepository nistagramUserRepository;
    private final LocationRepository locationRepository;
    private static final long DAY_IN_MILLISECONDS = 86400000L;
    private final StoryRepository storyRepository;
    private final ContentRepository contentRepository;

    @Value("${PROJECT_PATH}")
    private String project_path;

    @Autowired
    public StoryServiceImpl(NistagramUserRepository nistagramUserRepository, LocationRepository locationRepository, StoryRepository storyRepository, ContentRepository contentRepository) {
        this.nistagramUserRepository = nistagramUserRepository;
        this.locationRepository = locationRepository;
        this.storyRepository = storyRepository;
        this.contentRepository = contentRepository;
    }

    @Override
    public List<StoryDto> getActiveUserStories(String username) {
        NistagramUser user = getUser(username);
        var stories = user.getStories().stream().filter(s -> !passed24Hours(s.getDate()) && closeFriendsCheck(s, user))
                .collect(Collectors.toList());
        return makeDtos(stories);
    }

    private boolean closeFriendsCheck(Story story, NistagramUser storyOwner) {
        if (story.isOnlyCloseFriends()) {
            var user = getCurrentlyLoggedUser();
            if (user == null) {
                return false;
            }
            return storyOwner.getUsername().equals(user.getUsername()) ||
                    storyOwner.getCloseFriends().stream().anyMatch(nistagramUser -> nistagramUser.getUsername().equals(user.getUsername()));
        } else {
            return true;
        }
    }

    @Override
    public List<StoryDto> getHighlights(String username) {
        NistagramUser user = getUser(username);
        var stories = user.getStories().stream().filter(s -> !passed24Hours(s.getDate()) && s.isHighlights()
                && closeFriendsCheck(s, user))
                .collect(Collectors.toList());
        return makeDtos(stories);
    }

    private NistagramUser getUser(String username) {
        return nistagramUserRepository.findByUsername(username);
    }

    private List<StoryDto> makeDtos(List<Story> stories) {
        var dtos = new ArrayList<StoryDto>();
        stories.forEach(story -> {
            var location = story.getLocation();
            var locationDto = (location == null) ? null :
                    new LocationDto(location.getId(), location.getName());
            dtos.add(new StoryDto(story.getId(), story.getPath(),
                    locationDto,
                    story.getTagsList(), story.getDate()));
        });
        return dtos;
    }

    @Override
    public void createStory(CreateStoryDto storyDto, List<MultipartFile> files) throws IOException {
        Story story = new Story();

        Path story_path = Paths.get(project_path);
        MultipartFile file = files.get(0);
        String extension = Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1];
        String random = UUID.randomUUID().toString() + "." + extension;
        Files.copy(file.getInputStream(), story_path.resolve(random));

        story.setPath(random);
        story.setDate(Calendar.getInstance().getTime());
        story.setTagsList(storyDto.getTags());
        story.setOnlyCloseFriends(storyDto.isCloseFriends());
        story.setLocation(locationRepository.findByName(storyDto.getLocation()));
        story.setHighlights(storyDto.isHighlights());
        storyRepository.save(story);

        NistagramUser user = (NistagramUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Content> userContent = contentRepository.findAllByUserId(user.getId());
        userContent.add(story);
        user.setContent(userContent);
        nistagramUserRepository.save(user);
    }

    @Override
    public List<StoryDto> getLoggedUsersStories() {
        NistagramUser user = getCurrentlyLoggedUser();
        if(user==null){
            throw new UserNotLogged();
        }
        List<Story> userStories = storyRepository.findAllByUserId(user.getId());
        return makeDtos(userStories);
    }

    @Override
    public void removeStory(Long storyId) {
        storyRepository.deleteById(storyId);
    }

    private boolean passed24Hours(Date date) {
        return date.getTime() + DAY_IN_MILLISECONDS < (new Date().getTime());
    }

    private NistagramUser getCurrentlyLoggedUser() {
        var object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (NistagramUser.class.isInstance(object)) {
            return (NistagramUser) object;
        }
        return null;
    }
}
