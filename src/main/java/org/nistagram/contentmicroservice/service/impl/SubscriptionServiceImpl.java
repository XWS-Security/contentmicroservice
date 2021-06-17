package org.nistagram.contentmicroservice.service.impl;

import org.nistagram.contentmicroservice.data.dto.SubscriptionDto;
import org.nistagram.contentmicroservice.data.model.NistagramUser;
import org.nistagram.contentmicroservice.data.repository.NistagramUserRepository;
import org.nistagram.contentmicroservice.exceptions.UserNotLogged;
import org.nistagram.contentmicroservice.service.SubscriptionService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    private final NistagramUserRepository nistagramUserRepository;

    public SubscriptionServiceImpl(NistagramUserRepository nistagramUserRepository) {
        this.nistagramUserRepository = nistagramUserRepository;
    }

    @Override
    public void subscribe(SubscriptionDto dto) {
        NistagramUser subscribedTo = nistagramUserRepository.findByUsername(dto.getSubscribedTo());
        NistagramUser subscribedFrom = nistagramUserRepository.findByUsername(dto.getSubscribedFrom());

        List<NistagramUser> subscriptions = subscribedTo.getSubscribedUsers();
        if (!subscriptions.contains(subscribedFrom)) {
            subscriptions.add(subscribedFrom);
            nistagramUserRepository.save(subscribedTo);
        }
    }

    @Override
    public void unsubscribe(SubscriptionDto dto) {
        NistagramUser subscribedTo = nistagramUserRepository.findByUsername(dto.getSubscribedTo());
        NistagramUser subscribedFrom = nistagramUserRepository.findByUsername(dto.getSubscribedFrom());

        List<NistagramUser> subscriptions = subscribedTo.getSubscribedUsers();
        subscriptions.remove(subscribedFrom);
        nistagramUserRepository.save(subscribedTo);
    }

    private NistagramUser getCurrentlyLoggedUser() {
        var object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (object instanceof NistagramUser) {
            return (NistagramUser) object;
        }
        throw new UserNotLogged();
    }
}
