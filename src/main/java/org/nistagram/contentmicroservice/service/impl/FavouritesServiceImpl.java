package org.nistagram.contentmicroservice.service.impl;

import org.nistagram.contentmicroservice.data.dto.PostImageLinkDto;
import org.nistagram.contentmicroservice.data.model.NistagramUser;
import org.nistagram.contentmicroservice.data.model.content.Post;
import org.nistagram.contentmicroservice.data.repository.PostRepository;
import org.nistagram.contentmicroservice.data.repository.NistagramUserRepository;
import org.nistagram.contentmicroservice.exceptions.NotFoundException;
import org.nistagram.contentmicroservice.exceptions.UserNotLogged;
import org.nistagram.contentmicroservice.logging.LoggerService;
import org.nistagram.contentmicroservice.logging.LoggerServiceImpl;
import org.nistagram.contentmicroservice.service.IFavouritesService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FavouritesServiceImpl implements IFavouritesService {

    private final PostRepository postRepository;
    private final NistagramUserRepository nistagramUserRepository;
    private final LoggerService loggerService = new LoggerServiceImpl(this.getClass());

    public FavouritesServiceImpl(PostRepository postRepository, NistagramUserRepository nistagramUserRepository) {
        this.postRepository = postRepository;
        this.nistagramUserRepository = nistagramUserRepository;
    }

    @Override
    public List<PostImageLinkDto> getFavourites() {
        var user = getCurrentlyLoggedUser();
        if (user == null) {
            throw new UserNotLogged();
        }
        var posts = new ArrayList<PostImageLinkDto>();
        postRepository.findAllFavourites(user.getId()).forEach(post -> {
            var postOwner = nistagramUserRepository.findByContentContaining(post.getId());
            posts.add(new PostImageLinkDto(post.getPathsList().get(0), post.getId(), postOwner.getUsername(), postOwner.getProfilePictureName()));
        });
        return posts;
    }

    @Override
    public boolean inFavourites(Long postId) {
        var user = getCurrentlyLoggedUser();
        if (user == null) {
            throw new UserNotLogged();
        }
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isEmpty()) {
            throw new NotFoundException();
        }
        Post post = postOptional.get();
        return postRepository.findAllFavourites(user.getId()).contains(post);
    }

    @Override
    public void saveOrRemoveFavourite(Long postId) {
        var user = getCurrentlyLoggedUser();
        if (user == null) {
            loggerService.logTokenException("Session has expired.");
            throw new UserNotLogged();
        }
        var postOptional = postRepository.findById(postId);
        if (postOptional.isEmpty()) {
            throw new NotFoundException();
        }
        var post = postOptional.get();
        List<Post> favourites = postRepository.findAllFavourites(user.getId());
        if (favourites.contains(post)) {
            loggerService.logPostRemoveFromFavourites(postId);
            favourites.remove(post);
            loggerService.logPostRemoveFromFavouritesSuccess(postId);
        } else {
            loggerService.logPostSaveToFavourites(postId);
            favourites.add(post);
            loggerService.logPostSaveToFavouritesSuccess(postId);
        }
        user.setSavedContent(favourites);
        nistagramUserRepository.save(user);
    }

    private NistagramUser getCurrentlyLoggedUser() {
        var object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (NistagramUser.class.isInstance(object)) {
            return (NistagramUser) object;
        }
        return null;
    }
}
