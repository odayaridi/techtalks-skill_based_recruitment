package org.example.techtalksskillbasedrecruitment.candidateskillscore;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CandidateSkillScoreRepository extends JpaRepository<CandidateSkillScore,Integer> {
    Optional<CandidateSkillScore> findByCandidate_CandidateIdAndSkill_SkillId(Integer candidateId, Integer skillId);
}
