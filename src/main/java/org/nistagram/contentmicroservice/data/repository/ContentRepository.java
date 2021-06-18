package org.nistagram.contentmicroservice.data.repository;

import org.nistagram.contentmicroservice.data.dto.HomePageImageDto;
import org.nistagram.contentmicroservice.data.model.content.Content;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContentRepository extends CrudRepository<Content, Long> {
    @Query(value = "SELECT * FROM content WHERE user_id = :id", nativeQuery = true)
    List<Content> findAllByUserId(@Param("id") Long id);

    @Query(nativeQuery = true)
    List<HomePageImageDto> getSubscribedContent(@Param("id") Long id);
}
