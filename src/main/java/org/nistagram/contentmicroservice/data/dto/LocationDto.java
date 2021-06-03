package org.nistagram.contentmicroservice.data.dto;

import org.nistagram.contentmicroservice.data.model.Location;

import java.io.Serializable;

public class LocationDto implements Serializable {
    private Long id;
    private String name;

    public LocationDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public LocationDto() {
    }

    public LocationDto(Location location) {
        if (location != null) {
            this.id = location.getId();
            this.name = location.getName();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
