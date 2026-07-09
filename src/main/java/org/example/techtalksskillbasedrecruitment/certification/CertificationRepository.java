package org.example.techtalksskillbasedrecruitment.certification;

import org.example.techtalksskillbasedrecruitment.candidateprofile.CandidateProfile;
import org.example.techtalksskillbasedrecruitment.certification.dto.response.CertificationResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificationRepository extends JpaRepository<Certification,Integer> {
    boolean existsByCertificateFile(String certificateFile);

    List<Certification> findByCandidate_CandidateId(Integer candidateId);

}
