package org.nistagram.contentmicroservice.data.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.nistagram.contentmicroservice.data.enums.ReportType;
import org.nistagram.contentmicroservice.data.model.content.Content;

import javax.persistence.*;

@Entity
@Table(name = "Report")
public class Report {
    @Id
    @SequenceGenerator(name = "report_sequence_generator", sequenceName = "report_sequence", initialValue = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "report_sequence_generator")
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "reason")
    private String reason;

    @ManyToOne
    @JoinColumn(name = "content_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Content content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private NistagramUser user;

    @Column(name = "type")
    private ReportType reportType;

    public Report() {
    }

    public Report(String reason, Content content, NistagramUser user, ReportType reportType) {
        this.reason = reason;
        this.content = content;
        this.user = user;
        this.reportType = reportType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public NistagramUser getUser() {
        return user;
    }

    public void setUser(NistagramUser user) {
        this.user = user;
    }

    public ReportType getReportType() {
        return reportType;
    }

    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
    }
}
