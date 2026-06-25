package org.example.techtalksskillbasedrecruitment.matchscore;

import jakarta.persistence.*;
import org.example.techtalksskillbasedrecruitment.candidateprofile.CandidateProfile;
import org.example.techtalksskillbasedrecruitment.job.Job;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "match_scores",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_match_score",
                        columnNames = {"job_id", "candidate_id"}
                )
        }
)
public class MatchScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_id")
    private Integer matchId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @ManyToOne(optional = false)
    @JoinColumn(name = "candidate_id", nullable = false)
    private CandidateProfile candidate;

    @Column(name = "match_percentage", precision = 5, scale = 2)
    private BigDecimal matchPercentage;

    @Column(name = "calculated_at", insertable = false, updatable = false)
    private LocalDateTime calculatedAt;

    public MatchScore() {}

    public Integer getMatchId() { return matchId; }
    public void setMatchId(Integer matchId) { this.matchId = matchId; }

    public Job getJob() { return job; }
    public void setJob(Job job) { this.job = job; }

    public CandidateProfile getCandidate() { return candidate; }
    public void setCandidate(CandidateProfile candidate) { this.candidate = candidate; }

    public BigDecimal getMatchPercentage() { return matchPercentage; }
    public void setMatchPercentage(BigDecimal matchPercentage) { this.matchPercentage = matchPercentage; }

    public LocalDateTime getCalculatedAt() { return calculatedAt; }
}