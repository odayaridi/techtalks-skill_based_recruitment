package org.example.techtalksskillbasedrecruitment.modules.candidateskillscore;

import org.example.techtalksskillbasedrecruitment.modules.candidateprofile.CandidateProfile;
import org.example.techtalksskillbasedrecruitment.modules.skill.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidateSkillScoreRepository extends JpaRepository<CandidateSkillScore,Integer> {
    CandidateSkillScore findByCandidateAndSkill(CandidateProfile candidateProfile, Skill skill);
    void deleteByCandidate(CandidateProfile candidateProfile);
    List<CandidateSkillScore> findByCandidate(CandidateProfile candidateProfile);
}
