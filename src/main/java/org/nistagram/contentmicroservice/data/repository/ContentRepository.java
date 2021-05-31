package org.nistagram.contentmicroservice.data.repository;

import org.nistagram.contentmicroservice.data.model.content.Content;
import org.springframework.data.repository.CrudRepository;

public interface ContentRepository extends CrudRepository<Content, Long> {
}
