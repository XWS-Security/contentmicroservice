package org.nistagram.contentmicroservice.service;

import org.nistagram.contentmicroservice.data.dto.NotificationDto;
import org.nistagram.contentmicroservice.data.dto.NotificationSeenDto;
import org.nistagram.contentmicroservice.data.model.Notification;

import java.util.List;

public interface NotificationService {

    void setNotificationForLikes(NotificationDto notificationDto);

    void setNotificationForComments(NotificationDto notificationDto);

    void addUserToContentNotifications(NotificationDto notificationDto);

    void removeUserFromContentNotifications(NotificationDto notificationDto);

    void seen(NotificationSeenDto notificationSeenDto);

    List<Notification> getAll();
}
