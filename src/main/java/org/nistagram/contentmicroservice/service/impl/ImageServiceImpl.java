package org.nistagram.contentmicroservice.service.impl;

import org.nistagram.contentmicroservice.data.dto.MediaFileDto;
import org.nistagram.contentmicroservice.data.model.NistagramUser;
import org.nistagram.contentmicroservice.exceptions.BadFileTypeException;
import org.nistagram.contentmicroservice.service.IImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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
            throw new BadFileTypeException();
        }
    }

    @Override
    public String saveOne(List<MultipartFile> files) throws IOException {
        MultipartFile file = files.get(0);
        Path post_path = Paths.get(project_path);
        String extension = Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1];
        String random = UUID.randomUUID().toString() + "." + extension;
        Files.copy(file.getInputStream(), post_path.resolve(random));

        return random;
    }

    public static boolean isVideoFile(File file) {
        String mimeType = URLConnection.guessContentTypeFromName(file.getPath());
        return mimeType != null && mimeType.startsWith("video");
    }

    public static boolean isImageFile(File file) throws IOException {
        try {
            Image image = ImageIO.read(file);
            return image != null;
        } catch (IOException e) {
            throw e;
        }
    }
}
