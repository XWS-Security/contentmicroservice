package org.nistagram.contentmicroservice.data.model;

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

    public Report() {
    }

    public Report(Long id, String reason) {
        this.id = id;
        this.reason = reason;
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
}
