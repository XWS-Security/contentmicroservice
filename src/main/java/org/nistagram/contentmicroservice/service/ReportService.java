package org.nistagram.contentmicroservice.service;

import org.nistagram.contentmicroservice.data.dto.CreateReportDto;

public interface ReportService {

    void createReport (CreateReportDto createReportDto);
}
