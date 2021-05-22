package org.nistagram.contentmicroservice.data.model.content;

import org.neo4j.springframework.data.core.schema.Property;

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
}
