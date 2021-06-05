package org.nistagram.contentmicroservice.service.impl;

import org.nistagram.contentmicroservice.data.dto.ProfileImageDto;
import org.nistagram.contentmicroservice.data.dto.ProfileInfoDto;
import org.nistagram.contentmicroservice.data.model.NistagramUser;
import org.nistagram.contentmicroservice.data.model.content.Post;
import org.nistagram.contentmicroservice.data.repository.UserRepository;
import org.nistagram.contentmicroservice.service.IProfileService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileServiceImpl implements IProfileService {

    private final UserRepository userRepository;

    public ProfileServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private List<ProfileImageDto> getPathsOfFirstImagesInPosts(NistagramUser user){

        var posts = user.getPosts();
        var paths= new ArrayList<ProfileImageDto>();
        for (Post post : posts) {
            paths.add(new ProfileImageDto(post.getPaths().get(0), post.getId()));
        }
        return paths;
    }

    @Override
    public ProfileInfoDto getUserInfo(String id) {
        NistagramUser user = userRepository.findById(id).get();
        var hasStories = false;
        if(user.getStories().size()>0){
           hasStories=true;
        }
        return new ProfileInfoDto(getPathsOfFirstImagesInPosts(user),user.getAbout(), user.getProfilePictureName(), hasStories);
    }
}
