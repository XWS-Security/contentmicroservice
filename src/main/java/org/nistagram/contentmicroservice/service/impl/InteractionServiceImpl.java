package org.nistagram.contentmicroservice.service.impl;

import org.nistagram.contentmicroservice.data.enums.CloseFriends;
import org.nistagram.contentmicroservice.data.model.NistagramUser;
import org.nistagram.contentmicroservice.data.repository.UserRepository;
import org.nistagram.contentmicroservice.exceptions.UserAlreadyAdded;
import org.nistagram.contentmicroservice.exceptions.UserAlreadyRemoved;
import org.nistagram.contentmicroservice.exceptions.UserNotLogged;
import org.nistagram.contentmicroservice.service.IInteractionService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class InteractionServiceImpl implements IInteractionService {

    private final UserRepository userRepository;

    public InteractionServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public CloseFriends getCloseFriendStatus(String userName) {
        var loggedUser = getCurrentlyLoggedUser();
        if(loggedUser==null)
            return CloseFriends.USER_UNSIGNED;
        if(loggedUser.getUsername().equals(userName))
            return CloseFriends.SAME_USER;
        if(loggedUser.getCloseFriends().stream().anyMatch(nistagramUser -> nistagramUser.getUsername().equals(userName))){
            return CloseFriends.CLOSE_FRIENDS;
        }
        return  CloseFriends.NOT_CLOSE;
    }

    @Override
    public void removeCloseFriend(String username) {
        var loggedUser = getCurrentlyLoggedUser();
        if(loggedUser==null)
            throw new UserNotLogged();
        var user = userRepository.findByUsername(username);
        if(loggedUser.getCloseFriends().contains(user)){
            throw new UserAlreadyRemoved(username);
        }
        loggedUser.getCloseFriends().remove(user);
        userRepository.save(loggedUser);
    }

    @Override
    public void addCloseFriend(String username) {
        var loggedUser = getCurrentlyLoggedUser();
        if(loggedUser==null)
            throw new UserNotLogged();
        var user = userRepository.findByUsername(username);
        if(!loggedUser.getCloseFriends().contains(user)){
            throw new UserAlreadyAdded(username);
        }
        loggedUser.getCloseFriends().add(user);
        userRepository.save(loggedUser);
    }

    private NistagramUser getCurrentlyLoggedUser() {
        var object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (NistagramUser.class.isInstance(object)) {
            return (NistagramUser) object;
        }
        return null;
    }
}