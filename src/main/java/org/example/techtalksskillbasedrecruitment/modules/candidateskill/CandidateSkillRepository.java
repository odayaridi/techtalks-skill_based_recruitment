package org.example.techtalksskillbasedrecruitment.modules.candidateskill;

import org.example.techtalksskillbasedrecruitment.modules.candidateprofile.CandidateProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidateSkillRepository extends JpaRepository<CandidateSkill,Integer> {
    List<CandidateSkill> findByCandidate(CandidateProfile candidateProfile);
}
