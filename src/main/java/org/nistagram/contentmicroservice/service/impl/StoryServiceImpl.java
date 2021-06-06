package org.nistagram.contentmicroservice.service.impl;

import org.nistagram.contentmicroservice.data.dto.LocationDto;
import org.nistagram.contentmicroservice.data.dto.StoryDto;
import org.nistagram.contentmicroservice.data.model.NistagramUser;
import org.nistagram.contentmicroservice.data.model.content.Story;
import org.nistagram.contentmicroservice.data.repository.LocationRepository;
import org.nistagram.contentmicroservice.data.repository.UserRepository;
import org.nistagram.contentmicroservice.exceptions.NotFoundException;
import org.nistagram.contentmicroservice.service.IStoryService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoryServiceImpl implements IStoryService {

    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private static final long DAY_IN_MILLISECONDS = 86400000L;

    public StoryServiceImpl(UserRepository userRepository, LocationRepository locationRepository) {
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
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
            return storyOwner.getCloseFriends().contains(user);
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
        var optionalNistagramUser = userRepository.findById(username);
        if (optionalNistagramUser.isEmpty()) {
            throw new NotFoundException();
        }
        return optionalNistagramUser.get();
    }

    private List<StoryDto> makeDtos(List<Story> stories) {
        var dtos = new ArrayList<StoryDto>();
        stories.forEach(story -> {
            var location = locationRepository.findByContent(story.getId());
            var locationDto = (location == null) ? null :
                    new LocationDto(location.getId(), location.getName());
            dtos.add(new StoryDto(story.getId(), story.getPath(),
                    locationDto,
                    story.getTags(), story.getDate()));
        });
        return dtos;
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
