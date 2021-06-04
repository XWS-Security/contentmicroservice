package org.nistagram.contentmicroservice.service;


import org.nistagram.contentmicroservice.data.model.Role;

import java.util.List;

public interface AuthorityService {
    List<Role> findById(Long id);
    List<Role> findByname(String name);
}
