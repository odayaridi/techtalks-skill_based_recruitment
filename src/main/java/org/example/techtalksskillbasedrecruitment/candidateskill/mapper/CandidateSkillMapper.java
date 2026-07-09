package org.example.techtalksskillbasedrecruitment.candidateskill.mapper;

import org.example.techtalksskillbasedrecruitment.candidateskill.CandidateSkill;
import org.example.techtalksskillbasedrecruitment.candidateskill.dto.response.CandidateSkillResponse;
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
