package org.example.techtalksskillbasedrecruitment.candidateskill.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCandidateSkillRequest {
    private String skillName;
    private String level;
    private Integer yearsOfExperience;
}