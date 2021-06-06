package org.nistagram.contentmicroservice.controller;

import org.nistagram.contentmicroservice.data.dto.StoryDto;
import org.nistagram.contentmicroservice.exceptions.NotFoundException;
import org.nistagram.contentmicroservice.service.IStoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
