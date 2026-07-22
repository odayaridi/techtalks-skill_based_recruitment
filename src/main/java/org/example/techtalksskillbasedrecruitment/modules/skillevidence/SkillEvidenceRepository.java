package org.example.techtalksskillbasedrecruitment.modules.skillevidence;

import org.example.techtalksskillbasedrecruitment.modules.candidateprofile.CandidateProfile;
import org.example.techtalksskillbasedrecruitment.modules.skillevidence.dto.SkillAverageScoreDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SkillEvidenceRepository extends JpaRepository<SkillEvidence,Integer> {
    @Query("""
    SELECT new org.example.techtalksskillbasedrecruitment.modules.skillevidence.dto.SkillAverageScoreDto(
        se.skill.skillId,
        AVG(se.score)
    )
    FROM SkillEvidence se
    WHERE se.candidate.candidateId = :candidateId
    GROUP BY se.skill.skillId
""")
    List<SkillAverageScoreDto> findAverageScorePerSkill(Integer candidateId);



    void deleteByCandidateAndEvidenceTypeAndReferenceId(
            CandidateProfile candidate,
            String evidenceType,
            Integer referenceId
    );
}
