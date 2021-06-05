package org.nistagram.contentmicroservice.data.repository;


import org.nistagram.contentmicroservice.data.model.Role;
import org.springframework.data.repository.CrudRepository;

public interface AuthorityRepository extends CrudRepository<Role, Long> {
    Role findByName(String name);
}
