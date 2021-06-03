package org.nistagram.contentmicroservice.service;

import org.nistagram.contentmicroservice.data.dto.EditUserDto;
import org.nistagram.contentmicroservice.data.model.NistagramUser;

public interface UserService {
    void saveUser(NistagramUser user);

    void updateUser(EditUserDto editUserDto);
}
