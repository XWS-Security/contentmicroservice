package org.nistagram.contentmicroservice.data.repository;

import org.nistagram.contentmicroservice.data.model.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {
}
