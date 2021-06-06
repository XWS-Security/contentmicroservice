package org.nistagram.contentmicroservice.controller;

import org.modelmapper.ModelMapper;
import org.nistagram.contentmicroservice.data.dto.EditUserDto;
import org.nistagram.contentmicroservice.data.dto.ProfileInfoDto;
import org.nistagram.contentmicroservice.data.dto.UserDto;
import org.nistagram.contentmicroservice.data.model.NistagramUser;
import org.nistagram.contentmicroservice.exceptions.UserDoesNotExistException;
import org.nistagram.contentmicroservice.exceptions.UsernameAlreadyExistsException;
import org.nistagram.contentmicroservice.service.IProfileService;
import org.nistagram.contentmicroservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileController {

    private final IProfileService profileService;
    private final UserService userService;
    private final ModelMapper modelMapper = new ModelMapper();

    public ProfileController(IProfileService profileService, UserService userService) {
        this.profileService = profileService;
        this.userService = userService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProfileInfoDto> getUserInfo(@PathVariable String id) {
        try {
            var dtos = profileService.getUserInfo(id);
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/createNistagramUser")
    public ResponseEntity<String> createUser(@RequestBody @Valid UserDto userDto) {
        try {
            userService.saveUser(modelMapper.map(userDto, NistagramUser.class));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UsernameAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong.", HttpStatus.OK);
        }
    }

    @PutMapping("/updateUser")
    public ResponseEntity<String> updateUser(@RequestBody @Valid EditUserDto editUserDto) {
        try {
            userService.updateUser(editUserDto);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (UsernameAlreadyExistsException | UserDoesNotExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong.", HttpStatus.OK);
        }
    }

    @PreAuthorize("hasAuthority('NISTAGRAM_USER_ROLE')")
    @PostMapping(path = "/setPorfilePicture", consumes = {"multipart/form-data"})
    public ResponseEntity<String> setProfilePicture(@RequestPart("photos") List<MultipartFile> files) throws IOException {
        profileService.setProfilePicture(files);
        return null;
    }
}
