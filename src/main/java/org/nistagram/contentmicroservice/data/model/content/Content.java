package org.nistagram.contentmicroservice.data.model.content;

import com.vladmihalcea.hibernate.type.array.StringArrayType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.nistagram.contentmicroservice.data.dto.HomePageImageDto;
import org.nistagram.contentmicroservice.data.model.Location;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@SqlResultSetMapping(
        name = "SubscribedContentMapping",
        classes = {
                @ConstructorResult(
                        targetClass = HomePageImageDto.class,
                        columns = {
                                @ColumnResult(name = "username", type = String.class),
                                @ColumnResult(name = "profile_picture", type = String.class),
                                @ColumnResult(name = "post_id", type = Long.class)
                        }
                )
        }
)
@NamedNativeQuery(name = "Content.getSubscribedContent",
        query = "SELECT nu.username AS username, nu.profile_picture AS profile_picture, con.id AS post_id \n" +
                "FROM user_subscribed_user AS usu, nistagram_user AS nu, content AS con \n" +
                "WHERE usu.subscribed_user_id = :id \n" +
                "AND nu.id = usu.user_id AND con.user_id = nu.id AND con.content_type = 'POST' \n" +
                "ORDER BY con.date DESC;", resultSetMapping = "SubscribedContentMapping")
@TypeDefs({
        @TypeDef(
                name = "string-array",
                typeClass = StringArrayType.class
        )
})
@Entity
@Table(name = "content")
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
    private String[] tags;

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

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public List<String> getTagsList() {
        return Arrays.asList(tags.clone());
    }

    public void setTagsList(List<String> tags) {
        String[] array = new String[tags.size()];
        for (int i = 0; i < tags.size(); i++) {
            array[i] = tags.get(i);
        }
        this.tags = array;
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
