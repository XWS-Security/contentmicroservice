package org.nistagram.contentmicroservice.data.dto;

public class ReportDto {
    private String imageName;
    private Long id;
    private String userId;
    private String userProfileImageName;
    private Long reportedBy;

    public ReportDto(String imageName, Long id, String userId, String userProfileImageName, Long reportedBy) {
        this.imageName = imageName;
        this.id = id;
        this.userId = userId;
        this.userProfileImageName = userProfileImageName;
        this.reportedBy = reportedBy;
    }

    public ReportDto() {
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserProfileImageName() {
        return userProfileImageName;
    }

    public void setUserProfileImageName(String userProfileImageName) {
        this.userProfileImageName = userProfileImageName;
    }

    public Long getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(Long reportedBy) {
        this.reportedBy = reportedBy;
    }
}
