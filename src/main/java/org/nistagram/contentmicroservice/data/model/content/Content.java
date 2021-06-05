package org.nistagram.contentmicroservice.data.model.content;

import org.neo4j.springframework.data.core.schema.Id;
import org.neo4j.springframework.data.core.schema.Node;
import org.neo4j.springframework.data.core.schema.Property;
import org.neo4j.springframework.data.core.schema.Relationship;
import org.nistagram.contentmicroservice.data.model.Location;

import java.util.Date;
import java.util.List;

@Node("Content")
public abstract class Content {
    @Id
    private Long id;
    @Property("tags")
    private List<String> tags;
    @Property("date")
    private Date date;
    @Relationship(type = "LOCATED", direction = Relationship.Direction.INCOMING)
    private Location location;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Content{" +
                "id=" + id +
                ", tags=" + tags +
                ", date=" + date +
                ", location=" + location +
                '}';
    }
}
