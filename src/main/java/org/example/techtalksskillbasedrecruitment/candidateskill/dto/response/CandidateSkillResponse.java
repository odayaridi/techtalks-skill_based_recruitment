package org.example.techtalksskillbasedrecruitment.candidateskill.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CandidateSkillResponse {
    private Integer candidateSkillId;
    private Integer candidateId;
    private String skillName;
    private String level;
    private Integer yearsOfExperience;
}
