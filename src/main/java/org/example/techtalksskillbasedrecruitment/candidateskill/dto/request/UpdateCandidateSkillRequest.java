package org.example.techtalksskillbasedrecruitment.candidateskill.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCandidateSkillRequest {
    @NotBlank(message = "Skill name is required")
    private String skillName;

    @NotBlank(message = "Level is required")
    private String level;

    @NotNull(message = "Years of experience field is required")
    private Integer yearsOfExperience;
}
