package org.nistagram.contentmicroservice.controller;

import org.nistagram.contentmicroservice.data.dto.ProfileImageDto;
import org.nistagram.contentmicroservice.exceptions.NotFoundException;
import org.nistagram.contentmicroservice.service.ISearchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
public class SearchController {
    private final ISearchService searchService;

    public SearchController(ISearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/{searchParam}")
    public ResponseEntity<List<ProfileImageDto>> searchByTagsAndLocation(@PathVariable String searchParam) {
        try {
            var set = searchService.searchByTagAndLocation(searchParam);
            return new ResponseEntity<>(new ArrayList<>(set), HttpStatus.OK);
        } catch (NotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
