package org.nistagram.contentmicroservice.service.impl;

import org.nistagram.contentmicroservice.data.model.Role;
import org.nistagram.contentmicroservice.data.repository.RoleRepository;
import org.nistagram.contentmicroservice.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    private final RoleRepository roleRepository;

    @Autowired
    public AuthorityServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> findById(Long id) {
        Role auth = this.roleRepository.findById(id).get();
        List<Role> auths = new ArrayList<>();
        auths.add(auth);
        return auths;
    }

    @Override
    public List<Role> findByname(String name) {
        return this.roleRepository.findByName(name);
    }
}
