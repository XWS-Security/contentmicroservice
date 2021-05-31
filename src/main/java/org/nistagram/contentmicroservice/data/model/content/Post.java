package org.nistagram.contentmicroservice.data.model.content;

import org.neo4j.springframework.data.core.schema.Node;
import org.neo4j.springframework.data.core.schema.Property;
import org.neo4j.springframework.data.core.schema.Relationship;
import org.nistagram.contentmicroservice.data.model.Comment;
import org.nistagram.contentmicroservice.data.model.NistagramUser;

import java.util.List;
import java.util.Map;

@Node("Post")
public class Post extends Content{

    @Relationship(type = "LIKES", direction = Relationship.Direction.INCOMING)
    private List<NistagramUser> likes;
    @Relationship(type = "DISLIKES", direction = Relationship.Direction.INCOMING)
    private List<NistagramUser> dislikes;
    @Relationship(type = "COMMENTS", direction = Relationship.Direction.INCOMING)
    private Map<Comment, NistagramUser> comments;

    public Post() {
    }

    public List<NistagramUser> getLikes() {
        return likes;
    }

    public void setLikes(List<NistagramUser> likes) {
        this.likes = likes;
    }

    public List<NistagramUser> getDislikes() {
        return dislikes;
    }

    public void setDislikes(List<NistagramUser> dislikes) {
        this.dislikes = dislikes;
    }

    public Map<Comment, NistagramUser> getComments() {
        return comments;
    }

    public void setComments(Map<Comment, NistagramUser> comments) {
        this.comments = comments;
    }
}
