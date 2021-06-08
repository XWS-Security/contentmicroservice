package org.nistagram.contentmicroservice.data.repository;

import org.nistagram.contentmicroservice.data.model.content.Content;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContentRepository extends CrudRepository<Content, Long> {
    @Query(value = "SELECT c.content_type, c.id, c.date, c.tags, c.about, c.paths, c.highlights, c.only_close_friends, c.path, c.location_id " +
            " FROM content AS c, user_content AS uc" +
            " WHERE uc.user_id = :id AND c.id = uc.content_id", nativeQuery = true)
    List<Content> findAllByUserId (@Param("id") Long id);
}
