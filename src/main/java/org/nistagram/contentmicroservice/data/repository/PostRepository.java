package org.nistagram.contentmicroservice.data.repository;

import org.neo4j.springframework.data.repository.query.Query;
import org.nistagram.contentmicroservice.data.model.content.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {

    @Query("MATCH (p:Post) where p.id = $0 return p")
    Post findByIdentifier(long id);
}
