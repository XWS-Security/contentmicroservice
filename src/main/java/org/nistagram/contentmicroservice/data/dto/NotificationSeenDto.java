package org.nistagram.contentmicroservice.data.dto;

import java.io.Serializable;

public class NotificationSeenDto implements Serializable {
    private Long notificationId;

    public NotificationSeenDto() {
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }
}
