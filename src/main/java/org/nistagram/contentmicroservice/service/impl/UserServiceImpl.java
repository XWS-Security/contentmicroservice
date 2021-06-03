package org.nistagram.contentmicroservice.service.impl;

import org.nistagram.contentmicroservice.data.dto.EditUserDto;
import org.nistagram.contentmicroservice.data.model.NistagramUser;
import org.nistagram.contentmicroservice.data.repository.UserRepository;
import org.nistagram.contentmicroservice.exceptions.UserDoesNotExistException;
import org.nistagram.contentmicroservice.exceptions.UsernameAlreadyExistsException;
import org.nistagram.contentmicroservice.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(NistagramUser user) {
        if (isUsernameAvailable(user.getUsername())) {
            userRepository.save(user);
        } else {
            throw new UsernameAlreadyExistsException();
        }
    }

    @Override
    public void updateUser(EditUserDto editUserDto) {
        NistagramUser loadedUser = userRepository.findByUsername(editUserDto.getOldUsername());
        if (loadedUser == null) {
            throw new UserDoesNotExistException();
        }
        if (!editUserDto.getOldUsername().equals(editUserDto.getUsername()) && !isUsernameAvailable(editUserDto.getUsername())) {
            throw new UsernameAlreadyExistsException();
        }

        userRepository.updateProperties(loadedUser.getUsername(), editUserDto.getUsername(), editUserDto.isProfilePrivate());
    }

    private boolean isUsernameAvailable(String username) {
        NistagramUser user = userRepository.findByUsername(username);
        return user == null;
    }
}
