package org.nistagram.contentmicroservice.data.repository;

import org.neo4j.springframework.data.repository.query.Query;
import org.nistagram.contentmicroservice.data.model.Location;
import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<Location, Long> {
    Location findByName(String name);

    @Query("MATCH (n:Location)-[c:LOCATED]->(p:Content) WHERE p.id= $0 return n")
    Location findByContent(Long id);

}
