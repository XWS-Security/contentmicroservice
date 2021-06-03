package org.nistagram.contentmicroservice.service.impl;

import org.nistagram.contentmicroservice.data.dto.MediaFileDto;
import org.nistagram.contentmicroservice.exceptions.BadFileType;
import org.nistagram.contentmicroservice.service.IImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLConnection;

@Service
public class ImageServiceImpl implements IImageService {

    @Value("${PROJECT_PATH}")
    private String project_path;

    @Override
    public MediaFileDto getImage(String name) throws IOException {
        var file = new File(project_path + name);
        if (isImageFile(file)) {
            var stream = new InputStreamResource(new FileInputStream(file));
            return new MediaFileDto(stream, MediaType.IMAGE_JPEG, file);
        } else if (isVideoFile(file)) {
            var stream = new InputStreamResource(new FileInputStream(file));
            return new MediaFileDto(stream, MediaType.APPLICATION_OCTET_STREAM, file);
        } else {
            throw new BadFileType();
        }
    }

    public static boolean isVideoFile(File file) {
        String mimeType = URLConnection.guessContentTypeFromName(file.getPath());
        return mimeType != null && mimeType.startsWith("video");
    }

    public static boolean isImageFile(File file) throws IOException {
        try {
            Image image = ImageIO.read(file);
            if (image != null) {
                return true;
            }
            return false;
        } catch (IOException e) {
            throw e;
        }
    }
}
