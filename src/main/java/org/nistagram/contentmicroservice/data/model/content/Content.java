package org.nistagram.contentmicroservice.data.model.content;

import com.vladmihalcea.hibernate.type.array.StringArrayType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.nistagram.contentmicroservice.data.model.Location;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@TypeDefs({
        @TypeDef(
                name = "string-array",
                typeClass = StringArrayType.class
        )
})
@Entity
@Table(name = "Content")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "content_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Content {
    @Id
    @SequenceGenerator(name = "content_sequence_generator", sequenceName = "content_sequence", initialValue = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "content_sequence_generator")
    @Column(name = "id", unique = true)
    private Long id;

    @Type(type = "string-array")
    @Column(
            name = "tags",
            columnDefinition = "text[]"
    )
    private List<String> tags;

    @Column(name = "date")
    private Date date;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "location_id")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Content content = (Content) o;
        return id.equals(content.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
