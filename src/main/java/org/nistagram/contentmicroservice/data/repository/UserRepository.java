package org.nistagram.contentmicroservice.data.repository;

import org.nistagram.contentmicroservice.data.model.NistagramUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<NistagramUser, Long> {
    NistagramUser findByUsername(String username);

    @Query("SELECT DISTINCT u.id, u.username, u.enabled, u.lastPasswordResetDate, u.profilePicture, u.about, u.profilePrivate" +
            " FROM User AS u, Content AS c WHERE u.id=c.user_id AND c.id=:id")
    NistagramUser findByContentContaining(Long id);
}
