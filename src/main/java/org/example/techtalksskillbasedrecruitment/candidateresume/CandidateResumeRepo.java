package org.example.techtalksskillbasedrecruitment.candidateresume;

import org.example.techtalksskillbasedrecruitment.candidateprofile.CandidateProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateResumeRepo extends JpaRepository<CandidateResume,Integer> {
    CandidateResume findByCandidate(CandidateProfile candidateProfile);
    boolean existsByCandidate(CandidateProfile candidateProfile);
}
