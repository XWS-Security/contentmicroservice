package org.nistagram.contentmicroservice.data.repository;

import org.nistagram.contentmicroservice.data.model.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoleRepository extends CrudRepository<Role, Long> {
    @Query("MATCH (r:AuthRole {name: $0}) RETURN r")
    List<Role> findByName(String name);
}
