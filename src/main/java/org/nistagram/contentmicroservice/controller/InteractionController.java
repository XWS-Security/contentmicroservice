package org.nistagram.contentmicroservice.controller;

import org.nistagram.contentmicroservice.data.dto.PostsUserDto;
import org.nistagram.contentmicroservice.data.enums.CloseFriends;
import org.nistagram.contentmicroservice.service.IInteractionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/interaction", produces = MediaType.APPLICATION_JSON_VALUE)
public class InteractionController {

    private final IInteractionService interactionService;

    public InteractionController(IInteractionService interactionService) {
        this.interactionService = interactionService;
    }

    @GetMapping("/closeFriends/{username}")
    public ResponseEntity<CloseFriends> closeFriendStatus(@PathVariable String username) {
        try {
            var status = interactionService.getCloseFriendStatus(username);
            return new ResponseEntity<>(status, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/loggedUser")
    public ResponseEntity<PostsUserDto> loggedUserInfo() {
        try {
            PostsUserDto info = interactionService.getLoggedUserInfo();
            return new ResponseEntity<>(info, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/closeFriends/remove/{username}")
    public ResponseEntity<String> removeCloseFriend(@PathVariable String username) {
        try {
            interactionService.removeCloseFriend(username);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(":(",HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/closeFriends/add/{username}")
    public ResponseEntity<CloseFriends> addCloseFriend(@PathVariable String username) {
        try {
            interactionService.addCloseFriend(username);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
