package org.nistagram.contentmicroservice.data.model;

import org.neo4j.springframework.data.core.schema.Id;
import org.neo4j.springframework.data.core.schema.Node;
import org.neo4j.springframework.data.core.schema.Relationship;
import org.nistagram.contentmicroservice.data.model.content.Content;
import org.nistagram.contentmicroservice.data.model.content.Post;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Node("NistagramUser")
public class NistagramUser {
    @Id
    private String username;
    @Relationship(type = "IS_CLOSE_FRIEND", direction = Relationship.Direction.INCOMING)
    private List<NistagramUser> closeFriends;
    @Relationship(type = "SUBSCRIBED", direction = Relationship.Direction.INCOMING)
    private List<NistagramUser> subscribedUsers;
    @Relationship(type = "SAVED", direction = Relationship.Direction.INCOMING)
    private List<NistagramUser> savedContent;
    @Relationship(type = "REPORTED", direction = Relationship.Direction.INCOMING)
    private Map<Content, Report> reportedComments;
    @Relationship(type = "CONTENTS", direction = Relationship.Direction.INCOMING)
    private List<Content> content;
    private String profilePictureName;
    private String about;

    public List<Post> getPosts() {
        var posts = (List<Post>)(List<?>) getContent()
                .stream().filter(p->p.getClass().equals(Post.class)).collect(Collectors.toList());
        posts.sort(Comparator.comparing(Content::getDate));
        return posts;
    }

    public NistagramUser() {
    }

    public NistagramUser(String username, List<NistagramUser> closeFriends, List<NistagramUser> subscribedUsers, List<NistagramUser> savedContent, Map<Content, Report> reportedComments, List<Content> content, String profilePictureName, String about) {
        this.username = username;
        this.closeFriends = closeFriends;
        this.subscribedUsers = subscribedUsers;
        this.savedContent = savedContent;
        this.reportedComments = reportedComments;
        this.content = content;
        this.profilePictureName = profilePictureName;
        this.about = about;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public List<NistagramUser> getSavedContent() {
        return savedContent;
    }

    public void setSavedContent(List<NistagramUser> savedContent) {
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
}
