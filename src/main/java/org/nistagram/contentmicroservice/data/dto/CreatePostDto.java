package org.nistagram.contentmicroservice.data.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

public class CreatePostDto {

    private List<MultipartFile> files;
    private List<String> tags;
    private Date date;
    private String about;

    public CreatePostDto() {

    }

    public CreatePostDto(List<MultipartFile> files, List<String> tags, Date date, String about) {
        this.files = files;
        this.tags = tags;
        this.date = date;
        this.about = about;
    }

    public List<MultipartFile> getFiles() {
        return files;
    }

    public void setFiles(List<MultipartFile> files) {
        this.files = files;
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

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
