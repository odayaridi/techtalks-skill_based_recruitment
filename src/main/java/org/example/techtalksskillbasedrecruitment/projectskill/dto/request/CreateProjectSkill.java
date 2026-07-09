package org.example.techtalksskillbasedrecruitment.projectskill.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProjectSkill {
    @NotNull(message = "Project Id is required")
    private Integer projectId;
    @NotBlank(message = "Skill name is required")
    private String SkillName;
}
