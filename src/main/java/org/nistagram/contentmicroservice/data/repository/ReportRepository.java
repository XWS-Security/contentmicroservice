package org.nistagram.contentmicroservice.data.repository;

import org.nistagram.contentmicroservice.data.model.Report;
import org.nistagram.contentmicroservice.data.model.content.Post;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReportRepository extends CrudRepository<Report, Long> {

    @Modifying
    @Query(value = "DELETE FROM report WHERE content_id = :id", nativeQuery = true)
    void deleteReportsByContent(@Param("id") Long id);
}
