package org.nistagram.contentmicroservice.data.repository;

import org.nistagram.contentmicroservice.data.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
