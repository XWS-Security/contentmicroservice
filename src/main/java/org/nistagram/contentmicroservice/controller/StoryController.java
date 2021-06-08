package org.nistagram.contentmicroservice.controller;

import org.nistagram.contentmicroservice.data.dto.CreateStoryDto;
import org.nistagram.contentmicroservice.data.dto.StoryDto;
import org.nistagram.contentmicroservice.exceptions.NotFoundException;
import org.nistagram.contentmicroservice.service.IStoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/story", produces = MediaType.APPLICATION_JSON_VALUE)
public class StoryController {

    private final IStoryService storyService;

    public StoryController(IStoryService storyService) {
        this.storyService = storyService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<StoryDto>> getStories(@PathVariable String username) {
        try {
            var stories = storyService.getActiveUserStories(username);
            return ResponseEntity.ok().body(stories);
        } catch (NotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('NISTAGRAM_USER_ROLE')")
    @PostMapping(path = "/uploadContent", consumes = {"multipart/form-data"})
    public ResponseEntity<String> uploadContent(@RequestPart("obj") CreateStoryDto dto, @RequestPart("photos") List<MultipartFile> files) throws IOException {
        storyService.createStory(dto, files);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/highlights/{username}")
    public ResponseEntity<List<StoryDto>> getHighlights(@PathVariable String username) {
        try {
            var stories = storyService.getHighlights(username);
            return ResponseEntity.ok().body(stories);
        } catch (NotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/my")
    public ResponseEntity<List<StoryDto>> getLoggedUsersStories() {
        try {
            var stories = storyService.getLoggedUsersStories();
            return ResponseEntity.ok().body(stories);
        } catch (NotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
