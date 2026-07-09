package org.example.techtalksskillbasedrecruitment.candidateresume;

import jakarta.persistence.*;
import org.example.techtalksskillbasedrecruitment.candidateprofile.CandidateProfile;

import java.time.LocalDateTime;

@Entity
@Table(name = "candidate_resumes")
public class CandidateResume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resume_id")
    private Integer resumeId;

    @OneToOne(optional = false)
    @JoinColumn(name = "candidate_id", nullable = false, unique = true)
    private CandidateProfile candidate;

    @Column(name = "file_path", length = 255)
    private String filePath;

    @Column(name = "uploaded_at", insertable = false, updatable = false)
    private LocalDateTime uploadedAt = LocalDateTime.now();;

    public CandidateResume() {}

    public Integer getResumeId() { return resumeId; }
    public void setResumeId(Integer resumeId) { this.resumeId = resumeId; }

    public CandidateProfile getCandidate() { return candidate; }
    public void setCandidate(CandidateProfile candidate) { this.candidate = candidate; }

    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }

    public LocalDateTime getUploadedAt() { return uploadedAt; }
}