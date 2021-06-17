package org.nistagram.contentmicroservice.controller;

import org.modelmapper.ModelMapper;
import org.nistagram.contentmicroservice.data.dto.EditUserDto;
import org.nistagram.contentmicroservice.data.dto.ProfileInfoDto;
import org.nistagram.contentmicroservice.data.dto.UserDto;
import org.nistagram.contentmicroservice.data.model.NistagramUser;
import org.nistagram.contentmicroservice.exceptions.UserDoesNotExistException;
import org.nistagram.contentmicroservice.exceptions.UsernameAlreadyExistsException;
import org.nistagram.contentmicroservice.logging.LoggerService;
import org.nistagram.contentmicroservice.logging.LoggerServiceImpl;
import org.nistagram.contentmicroservice.service.IProfileService;
import org.nistagram.contentmicroservice.service.UserService;
import org.nistagram.contentmicroservice.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class ProfileController {

    private final IProfileService profileService;
    private final UserService userService;
    private final ModelMapper modelMapper = new ModelMapper();
    private final LoggerService loggerService = new LoggerServiceImpl(this.getClass());

    public ProfileController(IProfileService profileService, UserService userService) {
        this.profileService = profileService;
        this.userService = userService;
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<ProfileInfoDto> getUserInfo(@PathVariable("username") @Pattern(regexp = Constants.USERNAME_PATTERN, message = Constants.USERNAME_INVALID_MESSAGE) String id) {
        try {
            var dtos = profileService.getUserInfo(id);
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        } catch (Exception e) {
            loggerService.logException(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/createNistagramUser")
    public ResponseEntity<String> createUser(@RequestBody @Valid UserDto userDto) {
        try {
            loggerService.logCreateUser(userDto.getUsername());
            userService.saveUser(modelMapper.map(userDto, NistagramUser.class));
            loggerService.logCreateUserSuccess(userDto.getUsername());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UsernameAlreadyExistsException e) {
            loggerService.logCreateUserFailed(userDto.getUsername(), e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            loggerService.logCreateUserFailed(userDto.getUsername(), e.getMessage());
            return new ResponseEntity<>("Something went wrong.", HttpStatus.OK);
        }
    }

    @PutMapping(path = "/remove/{id}")
    @PreAuthorize("hasAuthority('ADMINISTRATOR')")
    public ResponseEntity<String> remove(@PathVariable Long id) {
        try {
            userService.removeUser(id);
            return new ResponseEntity<>("User successfully removed!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updateUser")
    public ResponseEntity<String> updateUser(@RequestBody @Valid EditUserDto editUserDto) {
        try {
            loggerService.logUpdateUser(editUserDto.getUsername());
            userService.updateUser(editUserDto);
            loggerService.logUpdateUserSuccess(editUserDto.getUsername());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UsernameAlreadyExistsException | UserDoesNotExistException e) {
            loggerService.logCreateUserFailed(editUserDto.getUsername(), e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            loggerService.logCreateUserFailed(editUserDto.getUsername(), e.getMessage());
            return new ResponseEntity<>("Something went wrong.", HttpStatus.OK);
        }
    }

    @PreAuthorize("hasAuthority('NISTAGRAM_USER_ROLE')")
    @PostMapping(path = "/setPorfilePicture", consumes = {"multipart/form-data"})
    public ResponseEntity<String> setProfilePicture(@RequestPart("photos") List<MultipartFile> files) throws IOException {
        profileService.setProfilePicture(files);
        return null;
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
