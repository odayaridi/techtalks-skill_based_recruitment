package org.example.techtalksskillbasedrecruitment.modules.certification;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificationRepository extends JpaRepository<Certification,Integer> {
    boolean existsByCertificateFile(String certificateFile);

    List<Certification> findByCandidate_CandidateId(Integer candidateId);

}
