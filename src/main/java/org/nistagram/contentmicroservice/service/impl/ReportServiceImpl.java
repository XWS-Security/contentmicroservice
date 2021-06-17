package org.nistagram.contentmicroservice.service.impl;

import org.nistagram.contentmicroservice.data.dto.CreateReportDto;
import org.nistagram.contentmicroservice.data.dto.ReportDto;
import org.nistagram.contentmicroservice.data.model.NistagramUser;
import org.nistagram.contentmicroservice.data.model.Report;
import org.nistagram.contentmicroservice.data.model.content.Content;
import org.nistagram.contentmicroservice.data.model.content.Post;
import org.nistagram.contentmicroservice.data.repository.ContentRepository;
import org.nistagram.contentmicroservice.data.repository.ReportRepository;
import org.nistagram.contentmicroservice.data.repository.NistagramUserRepository;
import org.nistagram.contentmicroservice.service.ReportService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final ContentRepository contentRepository;
    private final NistagramUserRepository nistagramUserRepository;

    public ReportServiceImpl(ReportRepository reportRepository, ContentRepository contentRepository, NistagramUserRepository nistagramUserRepository) {
        this.reportRepository = reportRepository;
        this.contentRepository = contentRepository;
        this.nistagramUserRepository = nistagramUserRepository;
    }

    @Override
    public void createReport(CreateReportDto createReportDto) {
        Content content = contentRepository.findById(createReportDto.getContentId()).get();
        NistagramUser user = (NistagramUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Report report = new Report(createReportDto.getReason(), content, user);
        reportRepository.save(report);
    }

    @Override
    public List<ReportDto> getAllReportedPosts() {
        ArrayList<Report> reports = (ArrayList<Report>) reportRepository.findAll();
        ArrayList<ReportDto> reportDtos = new ArrayList<>();
        reports.forEach(report -> {
            Post post = (Post) report.getContent();
            var postOwner = nistagramUserRepository.findByContentContaining(post.getId());
            reportDtos.add(new ReportDto(post.getPathsList().get(0), post.getId(), postOwner.getUsername(),
                    postOwner.getProfilePictureName(), report.getUser().getId()));
        });
        return reportDtos;
    }
}
