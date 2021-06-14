package org.nistagram.contentmicroservice.data.repository;

import org.nistagram.contentmicroservice.data.model.Report;
import org.springframework.data.repository.CrudRepository;

public interface ReportRepository extends CrudRepository<Report, Long> {
}
