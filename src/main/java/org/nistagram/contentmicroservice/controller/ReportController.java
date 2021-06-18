package org.nistagram.contentmicroservice.controller;

import org.nistagram.contentmicroservice.data.dto.CreateReportDto;
import org.nistagram.contentmicroservice.data.dto.ReportDto;
import org.nistagram.contentmicroservice.data.dto.StoryDto;
import org.nistagram.contentmicroservice.exceptions.NotFoundException;
import org.nistagram.contentmicroservice.service.ReportService;
import org.nistagram.contentmicroservice.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping(value = "/report", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PreAuthorize("hasAuthority('NISTAGRAM_USER_ROLE')")
    @PostMapping(path = "/")
    public ResponseEntity<String> createReport(@RequestBody CreateReportDto createReportDto) {
        try {
            reportService.createReport(createReportDto);
            return new ResponseEntity<>("Content reported successfully!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Report didn't send!", HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR_ROLE')")
    @GetMapping(path = "/post")
    public ResponseEntity<List<ReportDto>> getAllReportedPosts() {
        try {
            return new ResponseEntity<>(reportService.getAllReportedPosts(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('ADMINISTRATOR_ROLE')")
    @GetMapping(path = "/story")
    public ResponseEntity<List<StoryDto>> getAllReportedStories() {
        try {
            return new ResponseEntity<>(reportService.getAllReportedStories(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
