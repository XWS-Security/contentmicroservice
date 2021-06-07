package org.nistagram.contentmicroservice.data.model.content;

import com.vladmihalcea.hibernate.type.array.StringArrayType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.nistagram.contentmicroservice.data.model.Comment;
import org.nistagram.contentmicroservice.data.model.NistagramUser;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@TypeDefs({
        @TypeDef(
                name = "string-array",
                typeClass = StringArrayType.class
        )
})
@Entity
@DiscriminatorValue("POST")
public class Post extends Content {

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "post_likes",
            joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private List<NistagramUser> likes;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "post_dislikes",
            joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private List<NistagramUser> dislikes;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "post_comment",
            joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "comment_id", referencedColumnName = "id"))
    private List<Comment> comments;

    @Type(type = "string-array")
    @Column(
            name = "paths",
            columnDefinition = "text[]"
    )
    private String[] paths;

    @Column(name = "about")
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

    public String[] getPaths() {
        return paths;
    }

    public void setPaths(String[] paths) {
        this.paths = paths;
    }

//    public List<String> getPaths() {
//        return Arrays.asList(paths.clone());
//    }
//
//    public void setPaths(List<String> paths) {
//        this.paths = paths.toArray();
//    }

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
