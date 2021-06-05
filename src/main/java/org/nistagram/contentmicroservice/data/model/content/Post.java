package org.nistagram.contentmicroservice.data.model.content;

import org.neo4j.springframework.data.core.schema.Node;
import org.neo4j.springframework.data.core.schema.Property;
import org.neo4j.springframework.data.core.schema.Relationship;
import org.nistagram.contentmicroservice.data.model.Comment;
import org.nistagram.contentmicroservice.data.model.NistagramUser;

import java.util.ArrayList;
import java.util.List;

@Node("Post")
public class Post extends Content {

    @Relationship(type = "LIKES", direction = Relationship.Direction.INCOMING)
    private List<NistagramUser> likes;
    @Relationship(type = "DISLIKES", direction = Relationship.Direction.INCOMING)
    private List<NistagramUser> dislikes;
    @Relationship(type = "COMMENTS", direction = Relationship.Direction.INCOMING)
    private List<Comment> comments;
    @Property("paths")
    private List<String> paths;
    private String about;

    public Post() {
        likes = new ArrayList<>();
        dislikes = new ArrayList<>();
        comments = new ArrayList<>();
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
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

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<String> getPaths() {
        return paths;
    }
    public void setPaths(List<String> paths) {
        this.paths = paths;
    }

    @Override
    public String toString() {
        return "Post{" +
                "likes=" + likes +
                ", dislikes=" + dislikes +
                ", comments=" + comments +
                ", about='" + about + '\'' +
                '}';
    }
}
