package org.nistagram.contentmicroservice.service.impl;

import org.nistagram.contentmicroservice.data.dto.NotificationDto;
import org.nistagram.contentmicroservice.data.dto.NotificationSeenDto;
import org.nistagram.contentmicroservice.data.model.NistagramUser;
import org.nistagram.contentmicroservice.data.model.Notification;
import org.nistagram.contentmicroservice.data.repository.NistagramUserRepository;
import org.nistagram.contentmicroservice.data.repository.NotificationRepository;
import org.nistagram.contentmicroservice.exceptions.UserNotLogged;
import org.nistagram.contentmicroservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NistagramUserRepository nistagramUserRepository;
    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationServiceImpl(NistagramUserRepository nistagramUserRepository, NotificationRepository notificationRepository) {
        this.nistagramUserRepository = nistagramUserRepository;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void setNotificationForLikes(NotificationDto notificationDto) {
        NistagramUser user = (NistagramUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setNotificationLikes(notificationDto.getNotificationStatus());
        nistagramUserRepository.save(user);
    }

    @Override
    public void setNotificationForComments(NotificationDto notificationDto) {
        NistagramUser user = (NistagramUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user.setNotificationComments(notificationDto.getNotificationStatus());
        nistagramUserRepository.save(user);
    }

    @Override
    public void addUserToContentNotifications(NotificationDto notificationDto) {
        NistagramUser user = (NistagramUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        NistagramUser contentUser = nistagramUserRepository.findByUsername(notificationDto.getUsername());
        ArrayList<NistagramUser> contentUsers = (ArrayList<NistagramUser>) nistagramUserRepository.getContentUsers(user.getId());
        contentUsers.add(contentUser);
        user.setNotificationContent(contentUsers);
        nistagramUserRepository.save(user);
    }

    @Override
    public void removeUserFromContentNotifications(NotificationDto notificationDto) {
        NistagramUser user = (NistagramUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        NistagramUser contentUser = nistagramUserRepository.findByUsername(notificationDto.getUsername());
        ArrayList<NistagramUser> contentUsers = (ArrayList<NistagramUser>) nistagramUserRepository.getContentUsers(user.getId());
        contentUsers.remove(contentUser);
        user.setNotificationContent(contentUsers);
        nistagramUserRepository.save(user);
    }

    @Override
    public void seen(NotificationSeenDto notificationSeenDto) {
        var optional = notificationRepository.findById(notificationSeenDto.getNotificationId());
        if (optional.isEmpty()) return;
        var notification = optional.get();
        notification.setSeen(true);
        notificationRepository.save(notification);
    }

    @Override
    public List<Notification> getAll() {
        NistagramUser user = getCurrentlyLoggedUser();
        return notificationRepository.findByUser(user.getId());
    }

    private NistagramUser getCurrentlyLoggedUser() {
        var object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (object instanceof NistagramUser) {
            return (NistagramUser) object;
        }
        throw new UserNotLogged();
    }
}
