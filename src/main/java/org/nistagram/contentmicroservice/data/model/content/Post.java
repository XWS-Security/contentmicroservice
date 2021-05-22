package org.nistagram.contentmicroservice.data.model.content;

import org.neo4j.springframework.data.core.schema.Property;
import org.neo4j.springframework.data.core.schema.Relationship;
import org.nistagram.contentmicroservice.data.model.Comment;
import org.nistagram.contentmicroservice.data.model.NistagramUser;

import java.util.List;
import java.util.Map;

public class Post {
    @Property("paths")
    private List<String> paths;
    @Relationship(type = "LIKES", direction = Relationship.Direction.INCOMING)
    private List<NistagramUser> likes;
    @Relationship(type = "DISLIKES", direction = Relationship.Direction.INCOMING)
    private List<NistagramUser> dislikes;
    @Relationship(type = "LIKES", direction = Relationship.Direction.INCOMING)
    private Map<NistagramUser, Comment> comments;

    public Post() {
    }
}
