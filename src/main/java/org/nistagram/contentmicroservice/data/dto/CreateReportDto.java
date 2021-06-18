package org.nistagram.contentmicroservice.data.dto;

import org.nistagram.contentmicroservice.data.enums.ReportType;
import org.nistagram.contentmicroservice.util.Constants;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class CreateReportDto implements Serializable {

    @Min(1L)
    private Long contentId;

    @Pattern(regexp = Constants.PLAIN_TEXT_PATTERN, message = Constants.INVALID_CHARACTER_MESSAGE)
    private String reason;

    private ReportType reportType;

    public CreateReportDto() {

    }

    public CreateReportDto(Long contentId, String reason, ReportType reportType) {
        this.contentId = contentId;
        this.reason = reason;
        this.reportType = reportType;
    }

    public ReportType getReportType() {
        return reportType;
    }

    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
