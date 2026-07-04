package org.example.techtalksskillbasedrecruitment.candidateskill.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidateSkillResponse {
    private Integer candidateSkillId;
    private Integer candidateId;
    private String skillName;
    private String level;
    private Integer yearsOfExperience;
}