package org.nistagram.contentmicroservice.controller;

import org.nistagram.contentmicroservice.data.dto.*;
import org.nistagram.contentmicroservice.data.model.NistagramUser;
import org.nistagram.contentmicroservice.exceptions.AccessToUserProfileDeniedException;
import org.nistagram.contentmicroservice.exceptions.NotFoundException;
import org.nistagram.contentmicroservice.exceptions.UserNotLogged;
import org.nistagram.contentmicroservice.logging.LoggerService;
import org.nistagram.contentmicroservice.logging.LoggerServiceImpl;
import org.nistagram.contentmicroservice.service.IPostService;
import org.nistagram.contentmicroservice.service.PostReactionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.net.ssl.SSLException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/post", produces = MediaType.APPLICATION_JSON_VALUE)
public class PostController {

    private final IPostService postService;
    private final PostReactionService postReactionService;
    private final LoggerService loggerService = new LoggerServiceImpl(this.getClass());

    @Value("${PROJECT_PATH}")
    private String project_path;

    public PostController(IPostService postService, PostReactionService postReactionService) {
        this.postService = postService;
        this.postReactionService = postReactionService;
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

    @PreAuthorize("hasAuthority('NISTAGRAM_USER_ROLE')")
    @PostMapping(path = "/uploadContent", consumes = {"multipart/form-data"})
    public ResponseEntity<String> uploadContent(@RequestPart("obj") CreatePostDto dto, @RequestPart("photos") List<MultipartFile> files) throws SSLException {
        try {
            var username = getCurrentlyLoggedUser().getUsername();
            loggerService.logUploadPost(username);
            postService.createPost(dto, files);
            loggerService.logUploadPostSuccess(username);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserNotLogged e) {
            loggerService.logTokenException(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            loggerService.logUploadPostSuccess(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/comment")
    @PreAuthorize("hasAuthority('NISTAGRAM_USER_ROLE')")
    public ResponseEntity<String> comment(@RequestBody @Valid UploadCommentDto dto) {
        try {
            var username = getCurrentlyLoggedUser().getUsername();
            loggerService.logComment(dto.getPostId(), username);
            postReactionService.comment(dto);
            loggerService.logCommentSuccess(dto.getPostId(), username);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserNotLogged e) {
            loggerService.logTokenException(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (AccessToUserProfileDeniedException e) {
            loggerService.logCommentFailed(dto.getPostId(), e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            loggerService.logCommentFailed(dto.getPostId(), e.getMessage());
            return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/like")
    @PreAuthorize("hasAuthority('NISTAGRAM_USER_ROLE')")
    public ResponseEntity<String> like(@RequestBody @Valid UploadReactionToPostDto dto) {
        try {
            var username = getCurrentlyLoggedUser().getUsername();
            loggerService.logLike(username, dto.getPostId());
            postReactionService.like(dto.getPostId());
            loggerService.logLikeSuccess(username, dto.getPostId());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UserNotLogged e) {
            loggerService.logTokenException(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (AccessToUserProfileDeniedException e) {
            loggerService.logLikeFailed(dto.getPostId(), e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            loggerService.logLikeFailed(dto.getPostId(), e.getMessage());
            return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/dislike")
    @PreAuthorize("hasAuthority('NISTAGRAM_USER_ROLE')")
    public ResponseEntity<String> dislike(@RequestBody @Valid UploadReactionToPostDto dto) {
        try {
            var username = getCurrentlyLoggedUser().getUsername();
            loggerService.logDislike(username,dto.getPostId());
            postReactionService.dislike(dto.getPostId());
            loggerService.logDislikeSuccess(username, dto.getPostId());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (UserNotLogged e){
            loggerService.logTokenException(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (AccessToUserProfileDeniedException e) {
            loggerService.logDislikeFailed(dto.getPostId(),e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            loggerService.logDislikeFailed(dto.getPostId(),e.getMessage());
            return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        }
    }

    private NistagramUser getCurrentlyLoggedUser() {
        var object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (NistagramUser.class.isInstance(object)) {
            return (NistagramUser) object;
        }
        throw new UserNotLogged();
    }
}
