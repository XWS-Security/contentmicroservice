package org.nistagram.contentmicroservice.service.impl;

import org.nistagram.contentmicroservice.data.dto.ProfileImageDto;
import org.nistagram.contentmicroservice.data.repository.PostRepository;
import org.nistagram.contentmicroservice.service.ISearchService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SearchServiceImpl implements ISearchService {

    private final PostRepository postRepository;

    public SearchServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Set<ProfileImageDto> searchByLocation(String locationName) {
        var posts = postRepository.findAll();
        var dtos = new HashSet<ProfileImageDto>();
        posts.forEach(post -> {
            if (post.getLocation() != null) {
                if (post.getLocation().getName().toLowerCase().contains(locationName.toLowerCase())) {
                    dtos.add(new ProfileImageDto(post.getPaths().get(0), post.getId()));
                }
            }
        });
        return dtos;
    }

    @Override
    public Set<ProfileImageDto> searchByTag(String tag) {
        var posts = postRepository.findAll();
        var dtos = new HashSet<ProfileImageDto>();
        posts.forEach(post ->
        {
            if (post.getTags() != null) {
                post.getTags().forEach(t -> {
                    if (t.toLowerCase().contains(tag.toLowerCase())) {
                        dtos.add(new ProfileImageDto(post.getPaths().get(0), post.getId()));
                    }
                });
            }

        });
        return dtos;
    }

    @Override
    public Set<ProfileImageDto> searchByTagAndLocation(String name) {
        var tagSet = (HashSet) searchByTag(name);
        var locationSet = searchByLocation(name);
        tagSet.addAll(locationSet);
        return tagSet;
    }
}
