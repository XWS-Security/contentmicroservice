package org.nistagram.contentmicroservice.controller;

import org.nistagram.contentmicroservice.data.dto.PostsUserDto;
import org.nistagram.contentmicroservice.data.enums.CloseFriends;
import org.nistagram.contentmicroservice.data.model.NistagramUser;
import org.nistagram.contentmicroservice.exceptions.UserAlreadyAdded;
import org.nistagram.contentmicroservice.exceptions.UserAlreadyRemoved;
import org.nistagram.contentmicroservice.exceptions.UserNotLogged;
import org.nistagram.contentmicroservice.logging.LoggerService;
import org.nistagram.contentmicroservice.logging.LoggerServiceImpl;
import org.nistagram.contentmicroservice.service.IInteractionService;
import org.nistagram.contentmicroservice.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Pattern;

@RestController
@RequestMapping(value = "/interaction", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class InteractionController {

    private final IInteractionService interactionService;
    private final LoggerService loggerService = new LoggerServiceImpl(this.getClass());

    public InteractionController(IInteractionService interactionService) {
        this.interactionService = interactionService;
    }

    @GetMapping("/closeFriends/{username}")
    public ResponseEntity<CloseFriends> closeFriendStatus(@PathVariable @Pattern(regexp = Constants.PLAIN_TEXT_PATTERN, message = Constants.INVALID_CHARACTER_MESSAGE) String username) {
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
    public ResponseEntity<String> removeCloseFriend(@PathVariable @Pattern(regexp = Constants.PLAIN_TEXT_PATTERN, message = Constants.INVALID_CHARACTER_MESSAGE) String username) {
        try {
            var loggedUserUsername = getCurrentlyLoggedUser().getUsername();
            loggerService.logAddingToCloseFriend(loggedUserUsername,username);
            interactionService.removeCloseFriend(username);
            loggerService.logRemovingCloseFriendSuccess(loggedUserUsername, username);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (UserAlreadyRemoved e){
            loggerService.logRemovingCloseFriendFailed(username, e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (UserNotLogged e){
            loggerService.logTokenException(e.getMessage());
            loggerService.logRemovingCloseFriendFailed(username, e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {
            loggerService.logRemovingCloseFriendFailed(username, e.getMessage());
            return new ResponseEntity<>(":(",HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/closeFriends/add/{username}")
    public ResponseEntity<CloseFriends> addCloseFriend(@PathVariable @Pattern(regexp = Constants.PLAIN_TEXT_PATTERN, message = Constants.INVALID_CHARACTER_MESSAGE) String username) {
        try {
            var loggedUserUsername = getCurrentlyLoggedUser().getUsername();
            loggerService.logAddingToCloseFriend( getCurrentlyLoggedUser().getUsername(),username);
            interactionService.addCloseFriend(username);
            loggerService.logAddingToCloseFriendSuccess(loggedUserUsername, username);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (UserAlreadyAdded e){
            loggerService.logAddingToCloseFriendFailed(username, e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (UserNotLogged e){
            loggerService.logTokenException(e.getMessage());
            loggerService.logAddingToCloseFriendFailed(username, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (Exception e) {
            loggerService.logAddingToCloseFriendFailed(username, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private NistagramUser getCurrentlyLoggedUser() {
        var object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (NistagramUser.class.isInstance(object)) {
            return (NistagramUser) object;
        }
        throw new UserNotLogged();
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
