package org.nistagram.contentmicroservice.data.repository;

import org.nistagram.contentmicroservice.data.model.content.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
}
