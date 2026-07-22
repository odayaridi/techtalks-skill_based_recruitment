package org.example.techtalksskillbasedrecruitment.modules.jobskill.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateJobSkill {
    @NotNull (message = "Job id is required")
    private Integer jobId;

    @NotBlank(message = "Skill name is required")
    private String skillName;

    @NotNull(message = "Weight is required")
    private Integer weight;
}
