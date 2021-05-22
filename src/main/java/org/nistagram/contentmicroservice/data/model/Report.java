package org.nistagram.contentmicroservice.data.model;

import org.neo4j.springframework.data.core.schema.GeneratedValue;
import org.neo4j.springframework.data.core.schema.Id;
import org.neo4j.springframework.data.core.schema.Property;
import org.neo4j.springframework.data.core.schema.RelationshipProperties;

@RelationshipProperties()
public class Report {
    @Id
    @GeneratedValue
    private Long id;
    @Property("reason")
    private String reason;

    public Report() {
    }

    public Report(Long id, String reason) {
        this.id = id;
        this.reason = reason;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
