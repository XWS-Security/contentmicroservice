package org.nistagram.contentmicroservice.data.repository;

import org.nistagram.contentmicroservice.data.model.content.Story;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoryRepository extends CrudRepository<Story, Long> {

    @Query(value = "SELECT * FROM content WHERE content_type = 'STORY' AND user_id = :id", nativeQuery = true)
    List<Story> findAllByUserId(@Param("id") Long id);
}
