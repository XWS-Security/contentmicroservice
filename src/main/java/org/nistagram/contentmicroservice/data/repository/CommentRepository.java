package org.nistagram.contentmicroservice.data.repository;

import org.nistagram.contentmicroservice.data.model.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    @Query(value = "SELECT c.id, c.text, c.date FROM comment AS c, post_comment as pc " +
            "WHERE c.id = pc.comment_id AND pc.post_id = :postId", nativeQuery = true)
    List<Comment> findByPostId(long postId);
}
