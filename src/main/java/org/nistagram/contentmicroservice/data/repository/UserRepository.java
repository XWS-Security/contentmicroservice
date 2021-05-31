package org.nistagram.contentmicroservice.data.repository;

import org.nistagram.contentmicroservice.data.model.NistagramUser;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<NistagramUser, String> {
}
