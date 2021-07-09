package org.nistagram.contentmicroservice.data.dto;

import org.nistagram.contentmicroservice.data.enums.NotificationType;
import org.nistagram.contentmicroservice.data.model.Notification;

import java.io.Serializable;

public class GetNotificationDto implements Serializable {
    private Long id;
    private NotificationType notificationType;
    private long contentId;
    private String nistagramUser;
    private boolean seen;

    public GetNotificationDto() {
    }

    public GetNotificationDto(Notification notification) {
        this.id = notification.getId();
        this.notificationType = notification.getNotificationType();
        this.contentId = notification.getContent().getId();
        this.nistagramUser = notification.getNistagramUser().getUsername();
        this.seen = notification.isSeen();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public long getContentId() {
        return contentId;
    }

    public void setContentId(long contentId) {
        this.contentId = contentId;
    }

    public String getNistagramUser() {
        return nistagramUser;
    }

    public void setNistagramUser(String nistagramUser) {
        this.nistagramUser = nistagramUser;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }
}
