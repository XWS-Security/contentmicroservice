package org.nistagram.contentmicroservice.data.model;

import javax.persistence.*;

@Entity
@Table(name = "Location")
public class Location {
    @Id
    @SequenceGenerator(name = "location_sequence_generator", sequenceName = "location_sequence", initialValue = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location_sequence_generator")
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "name")
    private String name;

    public Location() {
    }

    public Location(Long id, String name) {
        this.id = id;
        this.name = name;
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
