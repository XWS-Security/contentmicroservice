package org.nistagram.contentmicroservice.data.model;

import org.nistagram.contentmicroservice.data.enums.Notifications;
import org.nistagram.contentmicroservice.data.model.content.Content;
import org.nistagram.contentmicroservice.data.model.content.Post;
import org.nistagram.contentmicroservice.data.model.content.Story;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@DiscriminatorValue("INSTAGRAM_USER")
public class NistagramUser extends User {
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_close_friend",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "close_friend_id", referencedColumnName = "id"))
    private List<NistagramUser> closeFriends;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_subscribed_user",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "subscribed_user_id", referencedColumnName = "id"))
    private List<NistagramUser> subscribedUsers;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_saved",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "saved_id")})
    private List<Post> savedContent;

    @OneToMany(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id")
    private List<Content> content;

    @OneToMany(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id")
    private List<Notification> notifications;

    @Column(name = "profile_picture")
    private String profilePictureName;

    @Column(name = "about")
    private String about;

    @Column(name = "private_profile")
    private Boolean profilePrivate;

    @Column(name = "tags_enabled")
    private boolean tagsEnabled = false;

    @Column(name = "notification_likes")
    private Notifications notificationLikes = Notifications.ON;

    @Column(name = "notification_comments")
    private Notifications notificationComments = Notifications.ON;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "notification_content",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_content_id")})
    private List<NistagramUser> notificationContent;

    public List<Post> getPosts() {
        var posts = (List<Post>) (List<?>) getContent()
                .stream().filter(p -> p.getClass().equals(Post.class)).collect(Collectors.toList());
        return posts;
    }

    public List<Story> getStories() {
        var stories = (List<Story>) (List<?>) getContent()
                .stream().filter(s -> s.getClass().equals(Story.class)).collect(Collectors.toList());
        return stories;
    }

    public NistagramUser() {
        this.closeFriends = new ArrayList<>();
        this.subscribedUsers = new ArrayList<>();
        this.savedContent = new ArrayList<>();
        this.content = new ArrayList<>();
    }

    public List<NistagramUser> getCloseFriends() {
        return closeFriends;
    }

    public void setCloseFriends(List<NistagramUser> closeFriends) {
        this.closeFriends = closeFriends;
    }

    public List<NistagramUser> getSubscribedUsers() {
        return subscribedUsers;
    }

    public void setSubscribedUsers(List<NistagramUser> subscribedUsers) {
        this.subscribedUsers = subscribedUsers;
    }

    public List<Post> getSavedContent() {
        return savedContent;
    }

    public void setSavedContent(List<Post> savedContent) {
        this.savedContent = savedContent;
    }

    public List<Content> getContent() {
        return content;
    }

    public void setContent(List<Content> content) {
        this.content = content;
    }

    public String getProfilePictureName() {
        return profilePictureName;
    }

    public void setProfilePictureName(String profilePictureName) {
        this.profilePictureName = profilePictureName;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Boolean getProfilePrivate() {
        return profilePrivate;
    }

    public void setProfilePrivate(Boolean profilePrivate) {
        this.profilePrivate = profilePrivate;
    }

    public boolean isTagsEnabled() {
        return tagsEnabled;
    }

    public void setTagsEnabled(boolean tagsEnabled) {
        this.tagsEnabled = tagsEnabled;
    }

    public Notifications getNotificationLikes() {
        return notificationLikes;
    }

    public void setNotificationLikes(Notifications notificationLikes) {
        this.notificationLikes = notificationLikes;
    }

    public Notifications getNotificationComments() {
        return notificationComments;
    }

    public void setNotificationComments(Notifications notificationComments) {
        this.notificationComments = notificationComments;
    }

    public List<NistagramUser> getNotificationContent() {
        return notificationContent;
    }

    public void setNotificationContent(List<NistagramUser> notificationContent) {
        this.notificationContent = notificationContent;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
}
