package org.nistagram.contentmicroservice.service.impl;

import org.nistagram.contentmicroservice.data.dto.NotificationDto;
import org.nistagram.contentmicroservice.data.dto.NotificationSeenDto;
import org.nistagram.contentmicroservice.data.model.NistagramUser;
import org.nistagram.contentmicroservice.data.model.Notification;
import org.nistagram.contentmicroservice.data.repository.NistagramUserRepository;
import org.nistagram.contentmicroservice.data.repository.NotificationRepository;
import org.nistagram.contentmicroservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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
        Notification notification = notificationRepository.findById(notificationSeenDto.getNotificationId()).get();
        notification.setSeen(true);
        notificationRepository.save(notification);
    }
}
