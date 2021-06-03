package org.nistagram.contentmicroservice.data.model;

import org.neo4j.springframework.data.core.schema.*;

import java.util.Date;

@RelationshipProperties()
public class Comment {
    @Id
    @GeneratedValue
    private Long id;
    @Property("text")
    private String text;
    @Property("Date")
    private Date date;
    @Relationship(type = "COMMENTATOR", direction = Relationship.Direction.INCOMING)
    private NistagramUser user;

    public Comment() {
    }

    public Comment(Long id, String text, Date date, NistagramUser user) {
        this.id = id;
        this.text = text;
        this.date = date;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date tDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public NistagramUser getUser() {
        return user;
    }

    public void setUser(NistagramUser user) {
        this.user = user;
    }
}
