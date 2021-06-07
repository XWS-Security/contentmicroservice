package org.nistagram.contentmicroservice.data.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Comment")
public class Comment {
    @Id
    @SequenceGenerator(name = "comment_sequence_generator", sequenceName = "comment_sequence", initialValue = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_sequence_generator")
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "text")
    private String text;

    @Column(name = "date")
    private Date date;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id")
    private NistagramUser user;

    public Comment() {
    }

    public Comment(String text, Date date, NistagramUser user) {
        this.text = text;
        this.date = date;
        this.user = user;
    }

    public Comment(Long id, String text, Date date, NistagramUser user) {
        this.id = id;
        this.text = text;
        this.date = date;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date tDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public NistagramUser getUser() {
        return user;
    }

    public void setUser(NistagramUser user) {
        this.user = user;
    }
}
