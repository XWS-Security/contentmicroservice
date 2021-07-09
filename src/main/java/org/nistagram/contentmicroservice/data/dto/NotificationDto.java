package org.nistagram.contentmicroservice.data.dto;

import org.nistagram.contentmicroservice.data.enums.Notifications;

import java.io.Serializable;

public class NotificationDto implements Serializable {
    private Notifications notificationStatus;
    private String username;

    public NotificationDto() {

    }

    public NotificationDto(Notifications notificationStatus, String username) {
        this.notificationStatus = notificationStatus;
        this.username = username;
    }

    public Notifications getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(Notifications notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
