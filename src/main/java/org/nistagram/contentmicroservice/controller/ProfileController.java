package org.nistagram.contentmicroservice.controller;

import org.nistagram.contentmicroservice.data.dto.ProfileInfoDto;
import org.nistagram.contentmicroservice.service.IProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileController {

    private final IProfileService profileService;

    public ProfileController(IProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfileInfoDto> getUserInfo(@PathVariable String id) {
        try {
            var dtos = profileService.getUserInfo(id);
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
