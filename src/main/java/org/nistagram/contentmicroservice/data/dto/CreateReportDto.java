package org.nistagram.contentmicroservice.data.dto;

import java.io.Serializable;

public class CreateReportDto implements Serializable {
    private Long contentId;
    private String reason;

    public CreateReportDto() {

    }

    public CreateReportDto(Long contentId, String reason) {
        this.contentId = contentId;
        this.reason = reason;
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
