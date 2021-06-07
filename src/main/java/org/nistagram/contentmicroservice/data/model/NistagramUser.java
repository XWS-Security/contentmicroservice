package org.nistagram.contentmicroservice.data.model;

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

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_saved",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "saved_id")})
    private List<Post> savedContent;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_content_report",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "content_report_id", referencedColumnName = "id")})
    @MapKeyJoinColumn(name = "content_id")
    private Map<Content, Report> reportedComments;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_content",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "content_id")})
    private List<Content> content;

    @Column(name = "profilePicture")
    private String profilePictureName;

    @Column(name = "about")
    private String about;

    @Column(name = "privateProfile")
    private Boolean profilePrivate;

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
        this.reportedComments = new HashMap<>();
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

    public Map<Content, Report> getReportedComments() {
        return reportedComments;
    }

    public void setReportedComments(Map<Content, Report> reportedComments) {
        this.reportedComments = reportedComments;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        NistagramUser that = (NistagramUser) o;
        return Objects.equals(closeFriends, that.closeFriends) &&
                Objects.equals(subscribedUsers, that.subscribedUsers) &&
                Objects.equals(savedContent, that.savedContent) &&
                Objects.equals(reportedComments, that.reportedComments) &&
                Objects.equals(content, that.content) &&
                Objects.equals(profilePictureName, that.profilePictureName) &&
                Objects.equals(about, that.about) &&
                Objects.equals(profilePrivate, that.profilePrivate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), closeFriends, subscribedUsers, savedContent, reportedComments, content, profilePictureName, about, profilePrivate);
    }
}
