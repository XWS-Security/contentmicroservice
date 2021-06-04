package org.nistagram.contentmicroservice.data.repository;


import org.nistagram.contentmicroservice.data.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
