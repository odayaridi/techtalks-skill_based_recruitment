package org.example.techtalksskillbasedrecruitment.certification;

import jakarta.persistence.*;
import org.example.techtalksskillbasedrecruitment.candidateprofile.CandidateProfile;

import java.time.LocalDate;

@Entity
@Table(name = "certifications")
public class Certification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certificate_id")
    private Integer certificateId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "candidate_id", nullable = false)
    private CandidateProfile candidate;

    @Column(name = "certificate_name", length = 150)
    private String certificateName;

    @Column(name = "issued_by", length = 150)
    private String issuedBy;

    @Column(name = "certificate_file", length = 255)
    private String certificateFile;

    @Column(name = "certificate_date")
    private LocalDate certificateDate;

    public Certification() {}

    public Integer getCertificateId() { return certificateId; }
    public void setCertificateId(Integer certificateId) { this.certificateId = certificateId; }

    public CandidateProfile getCandidate() { return candidate; }
    public void setCandidate(CandidateProfile candidate) { this.candidate = candidate; }

    public String getCertificateName() { return certificateName; }
    public void setCertificateName(String certificateName) { this.certificateName = certificateName; }

    public String getIssuedBy() { return issuedBy; }
    public void setIssuedBy(String issuedBy) { this.issuedBy = issuedBy; }

    public String getCertificateFile() { return certificateFile; }
    public void setCertificateFile(String certificateFile) { this.certificateFile = certificateFile; }

    public LocalDate getCertificateDate() { return certificateDate; }
    public void setCertificateDate(LocalDate certificateDate) { this.certificateDate = certificateDate; }
}