package org.example.techtalksskillbasedrecruitment.skillevidence;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillEvidenceRepository extends JpaRepository<SkillEvidence,Integer> {
    List<SkillEvidence> findByCandidate_CandidateId(Integer candidateId);
}
