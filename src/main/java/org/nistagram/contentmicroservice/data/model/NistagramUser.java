package org.nistagram.contentmicroservice.data.model;

import org.neo4j.springframework.data.core.schema.Id;
import org.neo4j.springframework.data.core.schema.Node;
import org.neo4j.springframework.data.core.schema.Relationship;
import org.nistagram.contentmicroservice.data.model.content.Content;
import org.nistagram.contentmicroservice.data.model.content.Post;

import java.util.List;
import java.util.Map;

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
    @Relationship(type = "SAVED", direction = Relationship.Direction.INCOMING)
    private Map<Content, Report> reportedComments;
    @Relationship(type = "SAVED", direction = Relationship.Direction.INCOMING)
    private Map<Post, Comment> commentedContents;

    public NistagramUser() {
    }

    public NistagramUser(String username, List<NistagramUser> closeFriends, List<NistagramUser> subscribedUsers, List<NistagramUser> savedContent, Map<Content, Report> reportedComments, Map<Post, Comment> commentedContents) {
        this.username = username;
        this.closeFriends = closeFriends;
        this.subscribedUsers = subscribedUsers;
        this.savedContent = savedContent;
        this.reportedComments = reportedComments;
        this.commentedContents = commentedContents;
    }
}
