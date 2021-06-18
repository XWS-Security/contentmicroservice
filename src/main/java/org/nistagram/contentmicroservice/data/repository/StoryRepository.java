package org.nistagram.contentmicroservice.data.repository;

import org.nistagram.contentmicroservice.data.model.content.Story;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoryRepository extends CrudRepository<Story, Long> {

    @Query(value = "SELECT c.id, c.tags, c.date, c.location_id, c.path, c.content_type, c.path, c.only_close_friends, c.highlights, c.deleted " +
            " FROM content AS c, user_content AS uc" +
            " WHERE c.content_type = 'STORY' AND uc.user_id = :id AND c.id = uc.content_id AND c.deleted = false", nativeQuery = true)
    List<Story> findAllByUserId (@Param("id") Long id);
}
