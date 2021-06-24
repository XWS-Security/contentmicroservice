package org.nistagram.contentmicroservice.data.repository;

import org.nistagram.contentmicroservice.data.model.content.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Long> {
    @Query(value = "SELECT * FROM content WHERE content_type = 'POST' AND user_id = :id", nativeQuery = true)
    List<Post> findAllByUserId(@Param("id") Long id);

    @Query(value = "SELECT c.id, c.tags, c.date, c.location_id, c.paths, c.about, c.content_type, c.user_id " +
            "FROM content AS c, user_saved AS us WHERE us.user_id = :id AND us.saved_id = c.id", nativeQuery = true)
    List<Post> findAllFavourites(@Param("id") Long id);
}
