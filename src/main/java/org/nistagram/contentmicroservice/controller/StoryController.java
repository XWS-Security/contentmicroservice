package org.nistagram.contentmicroservice.controller;

import org.nistagram.contentmicroservice.data.dto.CreateStoryDto;
import org.nistagram.contentmicroservice.data.dto.StoryDto;
import org.nistagram.contentmicroservice.data.model.NistagramUser;
import org.nistagram.contentmicroservice.exceptions.NotFoundException;
import org.nistagram.contentmicroservice.exceptions.UserNotLogged;
import org.nistagram.contentmicroservice.logging.LoggerService;
import org.nistagram.contentmicroservice.logging.LoggerServiceImpl;
import org.nistagram.contentmicroservice.service.IStoryService;
import org.nistagram.contentmicroservice.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/story", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class StoryController {

    private final IStoryService storyService;
    private final LoggerService loggerService = new LoggerServiceImpl(this.getClass());

    public StoryController(IStoryService storyService) {
        this.storyService = storyService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<StoryDto>> getStories(@PathVariable @Pattern(regexp = Constants.PLAIN_TEXT_PATTERN, message = Constants.INVALID_CHARACTER_MESSAGE) String username) {
        try {
            loggerService.getStories(username);
            var stories = storyService.getActiveUserStories(username);
            loggerService.getStoriesSuccess(username);
            return ResponseEntity.ok().body(stories);
        } catch (NotFoundException e) {
            loggerService.getStoriesFailed(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('NISTAGRAM_USER_ROLE')")
    @PostMapping(path = "/uploadContent", consumes = {"multipart/form-data"})
    public ResponseEntity<String> uploadContent(@RequestPart("obj") @Valid CreateStoryDto dto, @RequestPart("photos") List<MultipartFile> files) throws IOException {
        try {
            var username = getCurrentlyLoggedUser().getUsername();
            loggerService.logUploadStory(username);
            storyService.createStory(dto, files);
            loggerService.logUploadStorySuccess(username);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserNotLogged e) {
            loggerService.logUploadStoryFailed(e.getMessage());
            loggerService.logTokenException("User not logged in.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            loggerService.logUploadStoryFailed(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/highlights/{username}")
    public ResponseEntity<List<StoryDto>> getHighlights(@PathVariable String username) {
        try {
            loggerService.getStories(username);
            var stories = storyService.getHighlights(username);
            loggerService.getStoriesSuccess(username);
            return ResponseEntity.ok().body(stories);
        } catch (NotFoundException e) {
            loggerService.getStoriesFailed(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/my")
    public ResponseEntity<List<StoryDto>> getLoggedUsersStories() {
        try {
            var username = getCurrentlyLoggedUser().getUsername();
            loggerService.getStories(username);
            var stories = storyService.getLoggedUsersStories();
            loggerService.getStoriesSuccess(username);
            return ResponseEntity.ok().body(stories);
        } catch (NotFoundException e) {
            loggerService.getStoriesFailed(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (UserNotLogged e) {
            loggerService.getStoriesFailed(e.getMessage());
            loggerService.logTokenException(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            loggerService.getStoriesFailed(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
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

    private NistagramUser getCurrentlyLoggedUser() {
        var object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (NistagramUser.class.isInstance(object)) {
            return (NistagramUser) object;
        }
        throw new UserNotLogged();
    }
}
