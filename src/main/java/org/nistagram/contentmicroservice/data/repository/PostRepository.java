package org.nistagram.contentmicroservice.data.repository;

import org.nistagram.contentmicroservice.data.model.content.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {
    @Query(value = "SELECT c.id, c.tags, c.date, c.location_id, c.paths, c.about, c.content_type " +
            " FROM content AS c, user_content AS uc" +
            " WHERE c.content_type = 'POST' AND uc.user_id = :id AND c.id = uc.content_id", nativeQuery = true)
    List<Post> findAllByUserId (@Param("id") Long id);
}
