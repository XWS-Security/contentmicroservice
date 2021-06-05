package org.nistagram.contentmicroservice.data.model.content;

import org.neo4j.springframework.data.core.schema.Node;
import org.neo4j.springframework.data.core.schema.Property;

@Node("Story")
public class Story extends Content{
    @Property("path")
    private String path;
    @Property("onlyCloseFriends")
    private boolean onlyCloseFriends;
    @Property("highlights")
    private boolean highlights;

    public Story() {
        super();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isOnlyCloseFriends() {
        return onlyCloseFriends;
    }

    public void setOnlyCloseFriends(boolean onlyCloseFriends) {
        this.onlyCloseFriends = onlyCloseFriends;
    }

    public boolean isHighlights() {
        return highlights;
    }

    public void setHighlights(boolean highlights) {
        this.highlights = highlights;
    }
}
