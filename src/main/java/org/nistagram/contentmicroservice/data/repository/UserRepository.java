package org.nistagram.contentmicroservice.data.repository;

import org.neo4j.springframework.data.repository.query.Query;
import org.nistagram.contentmicroservice.data.model.NistagramUser;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<NistagramUser, String> {
    NistagramUser findByUsername(String username);

    @Query("MATCH (p:NistagramUser) WHERE p.username = $0 SET p.username = $1, p.private = $2 RETURN p")
    NistagramUser updateProperties(String oldUsername, String username, boolean profilePrivate);

    @Query("MATCH(p:Content)-[c:CONTENTS]->(n:NistagramUser) WHERE id(p)=$0 return n")
    NistagramUser findByContentContaining(Long id);
}
