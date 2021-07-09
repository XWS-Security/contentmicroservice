package org.nistagram.contentmicroservice.data.dto;

import java.io.Serializable;

public class NotificationSeenDto implements Serializable {
    private boolean notificationSeen;
    private Long notificationId;

    public NotificationSeenDto() {

    }

    public NotificationSeenDto(boolean notificationSeen, Long notificationId) {
        this.notificationSeen = notificationSeen;
        this.notificationId = notificationId;
    }

    public boolean isNotificationSeen() {
        return notificationSeen;
    }

    public void setNotificationSeen(boolean notificationSeen) {
        this.notificationSeen = notificationSeen;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }
}
