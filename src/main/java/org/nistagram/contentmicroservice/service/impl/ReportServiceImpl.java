package org.nistagram.contentmicroservice.service.impl;

import org.nistagram.contentmicroservice.data.dto.CreateReportDto;
import org.nistagram.contentmicroservice.data.model.NistagramUser;
import org.nistagram.contentmicroservice.data.model.Report;
import org.nistagram.contentmicroservice.data.model.content.Content;
import org.nistagram.contentmicroservice.data.repository.ContentRepository;
import org.nistagram.contentmicroservice.data.repository.ReportRepository;
import org.nistagram.contentmicroservice.service.ReportService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final ContentRepository contentRepository;

    public ReportServiceImpl(ReportRepository reportRepository, ContentRepository contentRepository) {
        this.reportRepository = reportRepository;
        this.contentRepository = contentRepository;
    }

    @Override
    public void createReport(CreateReportDto createReportDto) {
        Content content = contentRepository.findById(createReportDto.getContentId()).get();
        NistagramUser user = (NistagramUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Report report = new Report(createReportDto.getReason(), content, user);
        reportRepository.save(report);
    }
}
