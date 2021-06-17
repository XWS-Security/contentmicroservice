package org.nistagram.contentmicroservice.service.impl;

import org.nistagram.contentmicroservice.data.dto.PostsUserDto;
import org.nistagram.contentmicroservice.data.enums.CloseFriends;
import org.nistagram.contentmicroservice.data.model.NistagramUser;
import org.nistagram.contentmicroservice.data.repository.NistagramUserRepository;
import org.nistagram.contentmicroservice.exceptions.UserAlreadyAdded;
import org.nistagram.contentmicroservice.exceptions.UserAlreadyRemoved;
import org.nistagram.contentmicroservice.exceptions.UserNotLogged;
import org.nistagram.contentmicroservice.service.IInteractionService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InteractionServiceImpl implements IInteractionService {

    private final NistagramUserRepository nistagramUserRepository;

    public InteractionServiceImpl(NistagramUserRepository nistagramUserRepository) {
        this.nistagramUserRepository = nistagramUserRepository;
    }

    @Override
    public CloseFriends getCloseFriendStatus(String userName) {
        var loggedUser = getCurrentlyLoggedUser();
        if(loggedUser==null)
            return CloseFriends.USER_UNSIGNED;
        if(loggedUser.getUsername().equals(userName))
            return CloseFriends.SAME_USER;
        if(nistagramUserRepository.getCloseFriends(loggedUser.getId()).contains(nistagramUserRepository.findByUsername(userName))){
            return CloseFriends.CLOSE_FRIENDS;
        }
        return  CloseFriends.NOT_CLOSE;
    }

    @Override
    public void removeCloseFriend(String username) {
        var loggedUser = getCurrentlyLoggedUser();
        if(loggedUser==null)
            throw new UserNotLogged();
        var user = nistagramUserRepository.findByUsername(username);
        List<NistagramUser> closeFriends = nistagramUserRepository.getCloseFriends(loggedUser.getId());
        if(!closeFriends.contains(user)){
            throw new UserAlreadyRemoved(username);
        }
        closeFriends.remove(user);
        loggedUser.setCloseFriends(closeFriends);
        nistagramUserRepository.save(loggedUser);
    }

    @Override
    public void addCloseFriend(String username) {
        var loggedUser = getCurrentlyLoggedUser();
        if(loggedUser==null)
            throw new UserNotLogged();
        var user = nistagramUserRepository.findByUsername(username);
        List<NistagramUser> closeFriends = nistagramUserRepository.getCloseFriends(loggedUser.getId());
        if(closeFriends.contains(user)){
            throw new UserAlreadyAdded(username);
        }
        closeFriends.add(user);
        loggedUser.setCloseFriends(closeFriends);
        nistagramUserRepository.save(loggedUser);
    }

    @Override
    public PostsUserDto getLoggedUserInfo() {
        var user = getCurrentlyLoggedUser();
        if(user==null){
            return new PostsUserDto("","");
        }
        return new PostsUserDto(user.getUsername(), user.getProfilePictureName());
    }

    private NistagramUser getCurrentlyLoggedUser() {
        var object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (NistagramUser.class.isInstance(object)) {
            return (NistagramUser) object;
        }
        return null;
    }
}
