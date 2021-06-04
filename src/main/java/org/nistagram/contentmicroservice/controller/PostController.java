package org.nistagram.contentmicroservice.controller;

import org.nistagram.contentmicroservice.data.dto.CommentDto;
import org.nistagram.contentmicroservice.data.dto.PostDto;
import org.nistagram.contentmicroservice.data.dto.PostsUserDto;
import org.nistagram.contentmicroservice.exceptions.NotFoundException;
import org.nistagram.contentmicroservice.service.IPostService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    @PostMapping("/uploadContent")
    public ResponseEntity<String> uploadContent(@RequestParam("photos") List<MultipartFile> files) {
        System.out.println(project_path);
        Path path = Paths.get(project_path);
        files.forEach(file -> {
            if (file.isEmpty()) {
            //    throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
            }
            try {
                System.out.println(file.getOriginalFilename());
                Files.copy(file.getInputStream(), path.resolve(file.getOriginalFilename()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
