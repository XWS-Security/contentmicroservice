package org.nistagram.contentmicroservice.data.repository;

import org.nistagram.contentmicroservice.data.model.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    @Query("MATCH(p:Post)<-[c:COMMENTS]-(n:Comment) WHERE p.id=$0 return n")
    List<Comment> findByPost(long id);

    @Query("MATCH (p:Post), (u:NistagramUser) WHERE p.id = $0 AND u.username = $4 CREATE (c:Comment {text: $1, date: $2, id: $3}), (u)-[:COMMENTATOR]->(c), (c)-[:COMMENTS]->(p) RETURN c")
    Comment saveWithPost(long postId, String text, Date date, long commentId, String username);
}
