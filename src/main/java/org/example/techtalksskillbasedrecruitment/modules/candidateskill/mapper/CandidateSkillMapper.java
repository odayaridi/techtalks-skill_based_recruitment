package org.example.techtalksskillbasedrecruitment.modules.candidateskill.mapper;

import org.example.techtalksskillbasedrecruitment.modules.candidateskill.CandidateSkill;
import org.example.techtalksskillbasedrecruitment.modules.candidateskill.dto.response.CandidateSkillResponse;
import org.springframework.stereotype.Component;

@Component
public class CandidateSkillMapper {
    public CandidateSkillResponse toDTOResponse(CandidateSkill candidateSkill){
       return new CandidateSkillResponse
               (candidateSkill.getCandidateSkillId(),
                       candidateSkill.getCandidate().getCandidateId(),
                       candidateSkill.getSkill().getSkillName(),
                       candidateSkill.getLevel(),
                       candidateSkill.getYearsOfExperience());
    }
}
