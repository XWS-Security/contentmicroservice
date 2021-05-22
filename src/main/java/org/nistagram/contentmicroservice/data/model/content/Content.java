package org.nistagram.contentmicroservice.data.model.content;

import org.neo4j.springframework.data.core.schema.*;
import org.nistagram.contentmicroservice.data.model.Location;

import java.util.Calendar;
import java.util.List;

@Node("Content")
public abstract class Content {
    @Id
    @GeneratedValue
    private Long id;
    @Property("tags")
    private List<String> tags;
    @Property("date")
    private Calendar date;
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

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
