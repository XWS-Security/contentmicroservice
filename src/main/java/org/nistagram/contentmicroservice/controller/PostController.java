package org.nistagram.contentmicroservice.controller;

import org.nistagram.contentmicroservice.data.dto.CommentDto;
import org.nistagram.contentmicroservice.data.dto.CreatePostDto;
import org.nistagram.contentmicroservice.data.dto.LocationDto;
import org.nistagram.contentmicroservice.data.dto.PostDto;
import org.nistagram.contentmicroservice.data.dto.PostsUserDto;
import org.nistagram.contentmicroservice.exceptions.NotFoundException;
import org.nistagram.contentmicroservice.service.IPostService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.net.ssl.SSLException;
import java.util.List;

@RestController
@RequestMapping(value = "/post", produces = MediaType.APPLICATION_JSON_VALUE)
public class PostController {

    private final IPostService postService;

    @Value("${PROJECT_PATH}")
    private String project_path;

    public PostController(IPostService postService) {
        this.postService = postService;
    }

    @GetMapping("/images/{id}")
    public ResponseEntity<List<String>> getImages(@PathVariable Long id) {
        try {
            var list = postService.getImageNames(id);
            return ResponseEntity.ok().body(list);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/comment/{id}")
    public ResponseEntity<CommentDto> getComment(@PathVariable Long id) {
        try {
            var comment = postService.getComment(id);
            return ResponseEntity.ok().body(comment);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/locations")
    public ResponseEntity<List<LocationDto>> getLocations() {
        return new ResponseEntity<>(postService.getAllLocations(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long id) {
        try {
            var post = postService.getPostInfo(id);
            return ResponseEntity.ok().body(post);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("user/{id}")
    public ResponseEntity<PostsUserDto> getPostsUser(@PathVariable Long id) {
        try {
            var post = postService.getPostsUser(id);
            return ResponseEntity.ok().body(post);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

  //  @PreAuthorize("hasAuthority('NISTAGRAM_USER_ROLE')")
    @PostMapping(path = "/uploadContent", consumes = {"multipart/form-data"})
    public ResponseEntity<String> uploadContent(@RequestPart("obj") CreatePostDto dto, @RequestPart("photos") List<MultipartFile> files) throws SSLException {
        postService.createPost(dto, files);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
