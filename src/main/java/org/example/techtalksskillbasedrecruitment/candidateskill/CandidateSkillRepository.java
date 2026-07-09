package org.example.techtalksskillbasedrecruitment.candidateskill;

import org.example.techtalksskillbasedrecruitment.candidateprofile.CandidateProfile;
import org.example.techtalksskillbasedrecruitment.skill.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidateSkillRepository extends JpaRepository<CandidateSkill,Integer> {
    List<CandidateSkill> findByCandidate(CandidateProfile candidateProfile);
}
