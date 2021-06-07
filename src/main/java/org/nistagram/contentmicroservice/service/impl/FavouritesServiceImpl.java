package org.nistagram.contentmicroservice.service.impl;

import org.nistagram.contentmicroservice.data.dto.PostImageLinkDto;
import org.nistagram.contentmicroservice.data.model.NistagramUser;
import org.nistagram.contentmicroservice.data.repository.PostRepository;
import org.nistagram.contentmicroservice.data.repository.UserRepository;
import org.nistagram.contentmicroservice.exceptions.NotFoundException;
import org.nistagram.contentmicroservice.exceptions.UserNotLogged;
import org.nistagram.contentmicroservice.service.IFavouritesService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FavouritesServiceImpl implements IFavouritesService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public FavouritesServiceImpl(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<PostImageLinkDto> getFavourites() {
        var user = getCurrentlyLoggedUser();
        if (user == null) {
            throw new UserNotLogged();
        }
        var posts = new ArrayList<PostImageLinkDto>();
        user.getSavedContent().forEach(post -> {
            var postOwner = userRepository.findByContentContaining(post.getId());
            posts.add(new PostImageLinkDto(post.getPaths()[0], post.getId(), postOwner.getUsername(), postOwner.getProfilePictureName()));
        });
        return posts;
    }

    @Override
    public boolean inFavourites(Long postId) {
        var user = getCurrentlyLoggedUser();
        if (user == null) {
            throw new UserNotLogged();
        }
        return user.getSavedContent().stream().anyMatch(post -> post.getId().equals(postId));
    }

    @Override
    public void saveOrRemoveFavourite(Long postId) {
        var user = getCurrentlyLoggedUser();
        if (user == null) {
            throw new UserNotLogged();
        }
        var postOptional = postRepository.findById(postId);
        if(postOptional.isEmpty()){
            throw new NotFoundException();
        }
        var post = postOptional.get();
        if (user.getSavedContent().contains(post)) {
            user.getSavedContent().remove(post);
        } else {
            user.getSavedContent().add(post);
        }
        userRepository.save(user);
    }

    private NistagramUser getCurrentlyLoggedUser() {
        var object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (NistagramUser.class.isInstance(object)) {
            return (NistagramUser) object;
        }
        return null;
    }
}
