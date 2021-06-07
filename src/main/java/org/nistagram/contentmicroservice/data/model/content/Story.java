package org.nistagram.contentmicroservice.data.model.content;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("POST")
public class Story extends Content {
    @Column(name = "path")
    private String path;

    @Column(name = "onlyCloseFriends")
    private boolean onlyCloseFriends;

    @Column(name = "highlights")
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
