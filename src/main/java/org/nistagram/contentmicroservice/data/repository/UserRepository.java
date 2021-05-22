package org.nistagram.contentmicroservice.data.repository;

import org.neo4j.springframework.data.repository.Neo4jRepository;
import org.nistagram.contentmicroservice.data.model.NistagramUser;

public interface UserRepository extends Neo4jRepository<NistagramUser, String> {
}
