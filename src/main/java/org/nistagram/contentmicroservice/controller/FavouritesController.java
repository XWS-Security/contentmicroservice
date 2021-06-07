package org.nistagram.contentmicroservice.controller;

import org.nistagram.contentmicroservice.data.dto.PostImageLinkDto;
import org.nistagram.contentmicroservice.exceptions.NotFoundException;
import org.nistagram.contentmicroservice.service.IFavouritesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/favourites", produces = MediaType.APPLICATION_JSON_VALUE)
public class FavouritesController {

    private final IFavouritesService favouritesService;

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
    public ResponseEntity<String> saveOrRemoveFavourite(@PathVariable Long postId) {
        try {
            favouritesService.saveOrRemoveFavourite(postId);
            return ResponseEntity.ok().body("Post saved or removed from favourites");
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Boolean> isFavourite(@PathVariable Long postId) {
        try {
            var isFavourite = favouritesService.inFavourites(postId);
            return ResponseEntity.ok().body(isFavourite);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
