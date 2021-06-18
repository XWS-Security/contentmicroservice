package org.nistagram.contentmicroservice.service.impl;

import org.nistagram.contentmicroservice.data.dto.EditUserDto;
import org.nistagram.contentmicroservice.data.model.NistagramUser;
import org.nistagram.contentmicroservice.data.model.Role;
import org.nistagram.contentmicroservice.data.repository.RoleRepository;
import org.nistagram.contentmicroservice.data.repository.NistagramUserRepository;
import org.nistagram.contentmicroservice.exceptions.UserDoesNotExistException;
import org.nistagram.contentmicroservice.exceptions.UsernameAlreadyExistsException;
import org.nistagram.contentmicroservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final NistagramUserRepository nistagramUserRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(NistagramUserRepository nistagramUserRepository, RoleRepository roleRepository) {
        this.nistagramUserRepository = nistagramUserRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void saveUser(NistagramUser user) {
        if (isUsernameAvailable(user.getUsername())) {
            List<Role> roles = roleRepository.findByName(user.getAdministrationRole());
            user.setRoles(roles);
            nistagramUserRepository.save(user);
        } else {
            throw new UsernameAlreadyExistsException();
        }
    }

    @Override
    public void updateUser(EditUserDto editUserDto) {
        NistagramUser loadedUser = getCurrentlyLoggedUser();
        if (loadedUser == null) {
            throw new UserDoesNotExistException();
        }
        if (!editUserDto.getOldUsername().equals(editUserDto.getUsername()) && !isUsernameAvailable(editUserDto.getUsername())) {
            throw new UsernameAlreadyExistsException();
        }
        loadedUser.setProfilePrivate(editUserDto.isProfilePrivate());
        loadedUser.setUsername(editUserDto.getUsername());
        loadedUser.setAbout(editUserDto.getAbout());
        loadedUser.setTagsEnabled(editUserDto.isTagsEnabled());
        nistagramUserRepository.save(loadedUser);
    }

    @Override
    public void removeUser(Long userId) {
        nistagramUserRepository.deleteById(userId);
    }

    private boolean isUsernameAvailable(String username) {
        NistagramUser user = nistagramUserRepository.findByUsername(username);
        return user == null;
    }

    private NistagramUser getCurrentlyLoggedUser() {
        var object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (NistagramUser.class.isInstance(object)) {
            return (NistagramUser) object;
        }
        return null;
    }
}
