package org.nistagram.contentmicroservice.data.repository;

import org.nistagram.contentmicroservice.data.model.Location;
import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<Location, Long> {
}
