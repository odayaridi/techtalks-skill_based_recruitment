package org.example.techtalksskillbasedrecruitment.candidateskill.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCandidateSkillRequest {
    @NotNull(message = "Candidate id is required")
    private Integer candidateId;

    @NotBlank(message = "Skill name is required")
    private String skillName;

    private String level;

    private Integer yearsOfExperience;
}
