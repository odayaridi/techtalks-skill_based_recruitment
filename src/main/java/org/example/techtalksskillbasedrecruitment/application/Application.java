package org.example.techtalksskillbasedrecruitment.application;

import jakarta.persistence.*;
import org.example.techtalksskillbasedrecruitment.candidateprofile.CandidateProfile;
import org.example.techtalksskillbasedrecruitment.job.Job;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "applications",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_job_candidate",
                        columnNames = {"job_id", "candidate_id"}
                )
        }
)
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Integer applicationId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @ManyToOne(optional = false)
    @JoinColumn(name = "candidate_id", nullable = false)
    private CandidateProfile candidate;

    @Column(name = "status", length = 50)
    private String status;

    @Column(name = "applied_at", insertable = false, updatable = false)
    private LocalDateTime appliedAt;

    public Application() {}

    public Integer getApplicationId() { return applicationId; }
    public void setApplicationId(Integer applicationId) { this.applicationId = applicationId; }

    public Job getJob() { return job; }
    public void setJob(Job job) { this.job = job; }

    public CandidateProfile getCandidate() { return candidate; }
    public void setCandidate(CandidateProfile candidate) { this.candidate = candidate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getAppliedAt() { return appliedAt; }
}