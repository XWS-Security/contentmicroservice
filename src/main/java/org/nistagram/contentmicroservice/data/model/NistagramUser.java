package org.nistagram.contentmicroservice.data.model;

import org.neo4j.springframework.data.core.schema.*;
import org.nistagram.contentmicroservice.data.model.content.Content;
import org.nistagram.contentmicroservice.data.model.content.Post;
import org.nistagram.contentmicroservice.data.model.content.Story;

import java.util.*;
import java.util.stream.Collectors;

@Node("NistagramUser")
public class NistagramUser extends User {
    @Relationship(type = "IS_CLOSE_FRIEND", direction = Relationship.Direction.INCOMING)
    private List<NistagramUser> closeFriends;
    @Relationship(type = "SUBSCRIBED", direction = Relationship.Direction.INCOMING)
    private List<NistagramUser> subscribedUsers;
    @Relationship(type = "SAVED", direction = Relationship.Direction.INCOMING)
    private List<Post> savedContent;
    @Relationship(type = "REPORTED", direction = Relationship.Direction.INCOMING)
    private Map<Content, Report> reportedComments;
    @Relationship(type = "CONTENTS", direction = Relationship.Direction.INCOMING)
    private List<Content> content;
    private String profilePictureName;
    private String about;
    @Property("private")
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

//    public NistagramUser(String username, boolean enabled, Date lastPasswordResetDate, List<Role> roles) {
//        super(username, enabled, lastPasswordResetDate, roles);
//        this.closeFriends = new ArrayList<>();
//        this.subscribedUsers = new ArrayList<>();
//        this.savedContent = new ArrayList<>();
//        this.reportedComments = new HashMap<>();
//        this.content = new ArrayList<>();
//    }

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
}
