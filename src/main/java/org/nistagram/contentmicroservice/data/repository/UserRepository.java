package org.nistagram.contentmicroservice.data.repository;

import org.nistagram.contentmicroservice.data.model.NistagramUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<NistagramUser, Long> {
    NistagramUser findByUsername(String username);

    @Query(value = "SELECT DISTINCT u.id, u.user_type, u.username, u.enabled, u.last_password_reset_date, u.profile_picture," +
            " u.about, u.private_profile FROM nistagram_user AS u, content AS c, user_content AS uc" +
            " WHERE u.user_type = 'INSTAGRAM_USER' AND u.id = uc.user_id AND c.id = uc.content_id AND c.id = :id", nativeQuery = true)
    NistagramUser findByContentContaining(Long id);
}
