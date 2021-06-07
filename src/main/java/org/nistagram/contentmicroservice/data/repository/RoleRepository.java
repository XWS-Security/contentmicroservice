package org.nistagram.contentmicroservice.data.repository;

import org.nistagram.contentmicroservice.data.model.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoleRepository extends CrudRepository<Role, Long> {
    List<Role> findByName(String name);
}
