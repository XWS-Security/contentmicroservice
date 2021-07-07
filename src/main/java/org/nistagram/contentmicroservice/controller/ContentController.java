package org.nistagram.contentmicroservice.controller;

import org.nistagram.contentmicroservice.data.dto.ContentMessageInfoDto;
import org.nistagram.contentmicroservice.security.TokenUtils;
import org.nistagram.contentmicroservice.service.ContentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/content", produces = MediaType.APPLICATION_JSON_VALUE)
public class ContentController {
    private final TokenUtils tokenUtils;
    private final ContentService contentService;

    public ContentController(TokenUtils tokenUtils, ContentService contentService) {
        this.tokenUtils = tokenUtils;
        this.contentService = contentService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('NISTAGRAM_USER_ROLE')")
    public ResponseEntity<ContentMessageInfoDto> getContent(@PathVariable("id") Long id, HttpServletRequest request) {
        try {
            var result = contentService.getContentInfo(id, tokenUtils.getToken(request));
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
