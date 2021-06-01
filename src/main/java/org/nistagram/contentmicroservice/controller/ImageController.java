package org.nistagram.contentmicroservice.controller;

import org.nistagram.contentmicroservice.data.dto.ProfileImageDto;
import org.nistagram.contentmicroservice.service.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@RestController
@RequestMapping(value = "/image", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
public class ImageController {
    private final IImageService imageService;

    @Value("${PROJECT_PATH}")
    private String project_path;

    @Autowired
    public ImageController(IImageService imageService) {
        this.imageService = imageService;
    }


    @GetMapping("/{name}")
    public ResponseEntity<Resource> getImage(@PathVariable String name) {
        try {
            var img = imageService.getImage(name);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Content-Disposition",
                    "attachment; filename=\"" + img.getName() + "\"");
            responseHeaders.set("Content-Type", "image/jpeg");
            InputStreamResource resource = new InputStreamResource(new FileInputStream(img));
            return ResponseEntity.ok()
                    .headers(responseHeaders)
                    .contentLength(img.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
