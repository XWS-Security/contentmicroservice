package org.nistagram.contentmicroservice;

import org.nistagram.contentmicroservice.data.model.NistagramUser;
import org.nistagram.contentmicroservice.data.model.Role;
import org.nistagram.contentmicroservice.data.repository.RoleRepository;
import org.nistagram.contentmicroservice.data.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class ContentmicroserviceApplication {

    public static void main(String[] args) {
//        SpringApplication.run(ContentmicroserviceApplication.class, args);
        // TODO: Comment out if data already exists
        ApplicationContext ctx = SpringApplication.run(ContentmicroserviceApplication.class, args);

        // insert roles into database
        RoleRepository roleRepository = (RoleRepository) ctx.getBean("roleRepository");
        Role role = new Role("NISTAGRAM_USER_ROLE");
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        //roleRepository.save(role);

        // create one user for testing
        UserRepository userRepository = (UserRepository) ctx.getBean("userRepository");
        Date timestamp = new Date();
        NistagramUser user = new NistagramUser("luka", true, timestamp, roles);
        //userRepository.save(user);
    }
}