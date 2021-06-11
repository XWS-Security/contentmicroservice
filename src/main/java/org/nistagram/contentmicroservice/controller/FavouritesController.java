package org.nistagram.contentmicroservice.controller;

import org.nistagram.contentmicroservice.data.dto.PostImageLinkDto;
import org.nistagram.contentmicroservice.exceptions.NotFoundException;
import org.nistagram.contentmicroservice.exceptions.UserNotLogged;
import org.nistagram.contentmicroservice.logging.LoggerService;
import org.nistagram.contentmicroservice.logging.LoggerServiceImpl;
import org.nistagram.contentmicroservice.service.IFavouritesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping(value = "/favourites", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class FavouritesController {

    private final IFavouritesService favouritesService;
    private final LoggerService loggerService = new LoggerServiceImpl(this.getClass());

    public FavouritesController(IFavouritesService favouritesService) {
        this.favouritesService = favouritesService;
    }

    @GetMapping("")
    public ResponseEntity<List<PostImageLinkDto>> getFavourites() {
        try {
            var list = favouritesService.getFavourites();
            return ResponseEntity.ok().body(list);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{postId}")
    public ResponseEntity<String> saveOrRemoveFavourite(@PathVariable @Min(1L) Long postId) {
        try {
            favouritesService.saveOrRemoveFavourite(postId);
            return ResponseEntity.ok().body("Post saved or removed from favourites");
        } catch (UserNotLogged e) {
            loggerService.logTokenException("Token expired, user is logged off.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Boolean> isFavourite(@PathVariable @Min(1L) Long postId) {
        try {
            loggerService.logCheckingIfPostIsFavourite(postId);
            var isFavourite = favouritesService.inFavourites(postId);
            loggerService.logCheckingIfPostIsFavouriteSuccess(postId);
            return ResponseEntity.ok().body(isFavourite);
        } catch (UserNotLogged e) {
            loggerService.logCheckingIfPostIsFavouriteFailed(postId, e.getMessage());
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            loggerService.logCheckingIfPostIsFavouriteFailed(postId, e.getMessage());
            loggerService.logException(e.getMessage());
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
}
