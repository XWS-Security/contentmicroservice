package org.nistagram.contentmicroservice.data.dto;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;

import java.io.File;
import java.io.Serializable;

public class MediaFileDto implements Serializable {
    private InputStreamResource stream;
    private MediaType type;
    private File file;

    public MediaFileDto() {
    }

    public MediaFileDto(InputStreamResource stream, MediaType type, File file) {
        this.stream = stream;
        this.type = type;
        this.file = file;
    }

    public InputStreamResource getStream() {
        return stream;
    }

    public void setStream(InputStreamResource stream) {
        this.stream = stream;
    }

    public MediaType getType() {
        return type;
    }

    public void setType(MediaType type) {
        this.type = type;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
