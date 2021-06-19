package org.nistagram.contentmicroservice.controller;

import org.nistagram.contentmicroservice.exceptions.BadFileTypeException;
import org.nistagram.contentmicroservice.logging.LoggerService;
import org.nistagram.contentmicroservice.logging.LoggerServiceImpl;
import org.nistagram.contentmicroservice.service.IImageService;
import org.nistagram.contentmicroservice.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Pattern;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/image", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
@Validated
public class ImageController {

    private final IImageService imageService;

    @Autowired
    public ImageController(IImageService imageService) {
        this.imageService = imageService;
    }

    private final LoggerService loggerService = new LoggerServiceImpl(this.getClass());

    @GetMapping("/{name}")
    public ResponseEntity<Resource> getImage(@PathVariable @Pattern(regexp = Constants.PLAIN_TEXT_PATTERN, message = Constants.INVALID_CHARACTER_MESSAGE) String name) {
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
        } catch (Exception e) {
            loggerService.logGetImageFailed(name, e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('NISTAGRAM_USER_ROLE')")
    @PostMapping(path = "/save", consumes = {"multipart/form-data"}, produces = {"text/json"})
    public ResponseEntity<String> save(@RequestPart("photos") List<MultipartFile> files) throws IOException {
        var imageName = imageService.saveOne(files);
        return ResponseEntity.ok(imageName);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        loggerService.logValidationFailed(e.getMessage());
        return new ResponseEntity<>("Invalid characters in request", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        loggerService.logValidationFailed(e.getMessage());
        return new ResponseEntity<>("Invalid characters in request", HttpStatus.BAD_REQUEST);
    }
}
