package org.nistagram.contentmicroservice.service.impl;

import org.nistagram.contentmicroservice.data.dto.HomePageImageDto;
import org.nistagram.contentmicroservice.data.model.NistagramUser;
import org.nistagram.contentmicroservice.data.repository.ContentRepository;
import org.nistagram.contentmicroservice.exceptions.UserDoesNotExistException;
import org.nistagram.contentmicroservice.service.SubscribedContentService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscribedContentServiceImpl implements SubscribedContentService {
    private final ContentRepository contentRepository;

    public SubscribedContentServiceImpl(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    @Override
    public List<HomePageImageDto> getLatestContent() {
        NistagramUser user = getCurrentlyLoggedUser();
        if (user == null) {
            throw new UserDoesNotExistException();
        }
        return contentRepository.getSubscribedContent(user.getId());
    }

    private NistagramUser getCurrentlyLoggedUser() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equals("anonymousUser")) {
            return null;
        } else {
            return (NistagramUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
    }
}
