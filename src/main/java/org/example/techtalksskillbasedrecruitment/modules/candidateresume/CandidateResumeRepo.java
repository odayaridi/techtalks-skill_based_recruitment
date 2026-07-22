package org.example.techtalksskillbasedrecruitment.modules.candidateresume;

import org.example.techtalksskillbasedrecruitment.modules.candidateprofile.CandidateProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateResumeRepo extends JpaRepository<CandidateResume,Integer> {
    CandidateResume findByCandidate(CandidateProfile candidateProfile);
    boolean existsByCandidate(CandidateProfile candidateProfile);
}
