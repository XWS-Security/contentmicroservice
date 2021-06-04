package org.nistagram.contentmicroservice.data.repository;

import org.nistagram.contentmicroservice.data.model.User;
import org.springframework.data.repository.CrudRepository;

public interface AuthenticationUserRepository extends CrudRepository<User, Long> {

    User findNistagramUserByNistagramUsername(String nistagramUsername);

    User findByEmail(String email);
}
