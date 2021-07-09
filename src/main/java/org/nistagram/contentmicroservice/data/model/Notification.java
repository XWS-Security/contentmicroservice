package org.nistagram.contentmicroservice.data.model;

import org.nistagram.contentmicroservice.data.enums.NotificationType;
import org.nistagram.contentmicroservice.data.model.content.Content;

import javax.persistence.*;

@Entity
@Table(name = "Notification")
public class Notification {

    @Id
    @SequenceGenerator(name = "notification_sequence_generator", sequenceName = "notification_sequence", initialValue = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_sequence_generator")
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "type")
    private NotificationType notificationType;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "content_id")
    private Content content;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "caused_user_id")
    private NistagramUser nistagramUser;

    @Column(name = "seen")
    private boolean seen;

    public Notification() {

    }

    public Notification(NotificationType notificationType, Content content, NistagramUser nistagramUser, boolean seen) {
        this.notificationType = notificationType;
        this.content = content;
        this.nistagramUser = nistagramUser;
        this.seen = seen;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public NistagramUser getNistagramUser() {
        return nistagramUser;
    }

    public void setNistagramUser(NistagramUser nistagramUser) {
        this.nistagramUser = nistagramUser;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }
}
