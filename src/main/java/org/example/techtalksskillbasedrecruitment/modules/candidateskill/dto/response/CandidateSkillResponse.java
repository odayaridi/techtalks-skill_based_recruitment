package org.example.techtalksskillbasedrecruitment.modules.candidateskill.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CandidateSkillResponse {
    private Integer candidateSkillId;

    private Integer candidateId;

    private String skillName;

    private String level;

    private Integer yearsOfExperience;
}


