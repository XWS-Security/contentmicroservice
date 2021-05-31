package org.nistagram.contentmicroservice.data.model;

import org.neo4j.springframework.data.core.schema.GeneratedValue;
import org.neo4j.springframework.data.core.schema.Id;
import org.neo4j.springframework.data.core.schema.Property;
import org.neo4j.springframework.data.core.schema.RelationshipProperties;

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

    public Comment() {
    }

    public Comment(Long id, String text, Date date) {
        this.id = id;
        this.text = text;
        this.date = date;
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
}
