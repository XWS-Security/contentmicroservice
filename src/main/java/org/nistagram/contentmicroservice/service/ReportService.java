package org.nistagram.contentmicroservice.service;

import org.nistagram.contentmicroservice.data.dto.CreateReportDto;
import org.nistagram.contentmicroservice.data.dto.ReportDto;

import java.util.List;

public interface ReportService {

    void createReport (CreateReportDto createReportDto);

    List<ReportDto> getAllReportedPosts();
}
