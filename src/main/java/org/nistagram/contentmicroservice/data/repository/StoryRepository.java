package org.nistagram.contentmicroservice.data.repository;

import org.nistagram.contentmicroservice.data.model.content.Story;
import org.springframework.data.repository.CrudRepository;

public interface StoryRepository extends CrudRepository<Story, Long> {
}
