package org.nistagram.contentmicroservice.data.repository;

import org.neo4j.springframework.data.repository.query.Query;
import org.nistagram.contentmicroservice.data.model.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    @Query("MATCH(p:Post)<-[c:COMMENTS]-(n:Comment) WHERE p.id=$0 return n")
    List<Comment> findByPost(long id);
}
