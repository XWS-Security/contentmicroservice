package org.nistagram.contentmicroservice.data.model;

import org.neo4j.springframework.data.core.schema.*;

@Node("AuthRole")
public class Role {
    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "name")
    private String name;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
