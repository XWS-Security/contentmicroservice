package org.nistagram.contentmicroservice.service.impl;

import org.nistagram.contentmicroservice.data.dto.ProfileImageDto;
import org.nistagram.contentmicroservice.data.dto.ProfileInfoDto;
import org.nistagram.contentmicroservice.data.model.NistagramUser;
import org.nistagram.contentmicroservice.data.model.content.Post;
import org.nistagram.contentmicroservice.data.model.content.Story;
import org.nistagram.contentmicroservice.data.repository.NistagramUserRepository;
import org.nistagram.contentmicroservice.service.IImageService;
import org.nistagram.contentmicroservice.service.IProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileServiceImpl implements IProfileService {

    private final NistagramUserRepository nistagramUserRepository;
    private final IImageService imageService;

    @Value("${PROJECT_PATH}")
    private String project_path;

    @Autowired
    public ProfileServiceImpl(NistagramUserRepository nistagramUserRepository, IImageService imageService) {
        this.nistagramUserRepository = nistagramUserRepository;
        this.imageService = imageService;
    }

    private List<ProfileImageDto> getPathsOfFirstImagesInPosts(NistagramUser user) {

        var posts = user.getPosts();
        var paths = new ArrayList<ProfileImageDto>();
        for (Post post : posts) {
            paths.add(new ProfileImageDto(post.getPathsList().get(0), post.getId()));
        }
        return paths;
    }

    @Override
    public ProfileInfoDto getUserInfo(String id) {
        NistagramUser user = nistagramUserRepository.findByUsername(id);

        var hasStories = false;
        var stories = user.getStories();

        if (stories.size() > 0) {
            hasStories = true;
        }
        var hasHighlights = stories.stream().anyMatch(Story::isHighlights);
        return new ProfileInfoDto(getPathsOfFirstImagesInPosts(user), user.getAbout(), user.getProfilePictureName(), hasStories, hasHighlights);
    }

    @Override
    public void setProfilePicture(List<MultipartFile> files) throws IOException {
        NistagramUser user = (NistagramUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        var imageName = imageService.saveOne(files);
        user.setProfilePictureName(imageName);
        nistagramUserRepository.save(user);
    }
}
