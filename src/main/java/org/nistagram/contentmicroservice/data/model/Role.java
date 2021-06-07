package org.nistagram.contentmicroservice.data.model;

import javax.persistence.*;

@Entity
@Table(name = "AuthRole")
public class Role {
    @Id
    @SequenceGenerator(name = "role_sequence_generator", sequenceName = "role_sequence", initialValue = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_sequence_generator")
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "name")
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
