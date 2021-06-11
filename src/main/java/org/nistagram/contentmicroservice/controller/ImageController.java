package org.nistagram.contentmicroservice.controller;

import org.nistagram.contentmicroservice.exceptions.BadFileTypeException;
import org.nistagram.contentmicroservice.logging.LoggerService;
import org.nistagram.contentmicroservice.logging.LoggerServiceImpl;
import org.nistagram.contentmicroservice.service.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/image", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
public class ImageController {

    private final IImageService imageService;

    @Autowired
    public ImageController(IImageService imageService) {
        this.imageService = imageService;
    }

    private final LoggerService loggerService = new LoggerServiceImpl(this.getClass());

    @GetMapping("/{name}")
    public ResponseEntity<Resource> getImage(@PathVariable String name) {
        try {
            loggerService.logGetImage(name);
            var media = imageService.getImage(name);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Content-Disposition",
                    "attachment; filename=\"" + media.getFile().getName() + "\"");
            responseHeaders.set("Content-Type", media.getType().toString());
            MediaType type = media.getType();
            loggerService.logGetImageSuccess(name);
            return ResponseEntity.ok()
                    .headers(responseHeaders)
                    .contentLength(media.getFile().length())
                    .contentType(type)
                    .body(media.getStream());
        } catch (BadFileTypeException e) {
            loggerService.logGetImageFailed(name, e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            loggerService.logGetImageFailed(name, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
