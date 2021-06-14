package org.nistagram.contentmicroservice.service.impl;

import org.nistagram.contentmicroservice.data.dto.SubscriptionDto;
import org.nistagram.contentmicroservice.data.model.NistagramUser;
import org.nistagram.contentmicroservice.data.repository.UserRepository;
import org.nistagram.contentmicroservice.exceptions.UserNotLogged;
import org.nistagram.contentmicroservice.service.SubscriptionService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    private final UserRepository userRepository;

    public SubscriptionServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void subscribe(SubscriptionDto dto) {
        NistagramUser subscribedTo = userRepository.findByUsername(dto.getSubscribedTo());
        NistagramUser subscribedFrom = userRepository.findByUsername(dto.getSubscribedFrom());

        List<NistagramUser> subscriptions = subscribedTo.getSubscribedUsers();
        subscriptions.add(subscribedFrom);
        userRepository.save(subscribedTo);
    }

    @Override
    public void unsubscribe(SubscriptionDto dto) {
        NistagramUser subscribedTo = userRepository.findByUsername(dto.getSubscribedTo());
        NistagramUser subscribedFrom = userRepository.findByUsername(dto.getSubscribedFrom());

        List<NistagramUser> subscriptions = subscribedTo.getSubscribedUsers();
        subscriptions.remove(subscribedFrom);
        userRepository.save(subscribedTo);
    }

    private NistagramUser getCurrentlyLoggedUser() {
        var object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (object instanceof NistagramUser) {
            return (NistagramUser) object;
        }
        throw new UserNotLogged();
    }
}
