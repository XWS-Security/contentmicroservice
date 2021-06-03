package org.nistagram.contentmicroservice.data.dto;

import java.util.Date;

public class CommentDto {
    private String username;
    private Date date;
    private String imageName;
    private String text;
    private Long id;

    public CommentDto(String username, Date date, String imageName, String text, Long id) {
        this.username = username;
        this.date = date;
        this.imageName = imageName;
        this.text = text;
        this.id = id;
    }

    public CommentDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
