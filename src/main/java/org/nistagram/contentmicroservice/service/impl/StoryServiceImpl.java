package org.nistagram.contentmicroservice.service.impl;

import org.nistagram.contentmicroservice.data.dto.LocationDto;
import org.nistagram.contentmicroservice.data.dto.StoryDto;
import org.nistagram.contentmicroservice.data.repository.LocationRepository;
import org.nistagram.contentmicroservice.data.repository.UserRepository;
import org.nistagram.contentmicroservice.exceptions.NotFoundException;
import org.nistagram.contentmicroservice.service.IStoryService;
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
        var optionalNistagramUser = userRepository.findById(username);
        if (optionalNistagramUser.isEmpty()) {
            throw new NotFoundException();
        }
        var user = optionalNistagramUser.get();
        var stories = user.getStories().stream().filter(s -> !passed24Hours(s.getDate()))
                .collect(Collectors.toList());
        var dtos = new ArrayList<StoryDto>();
        stories.forEach(story -> {
            var location =  locationRepository.findByStory(story.getId());
            var locationDto = (location==null)? new LocationDto(null, null) :
                    new LocationDto(location.getId(),location.getName());
            dtos.add(new StoryDto(story.getId(),story.getPath(),
                    locationDto,
                    story.getTags(), story.getDate()));
        });
        return dtos;
    }

    private boolean passed24Hours(Date date) {
        return  date.getTime() + DAY_IN_MILLISECONDS < (new Date().getTime()) ;
    }
}
