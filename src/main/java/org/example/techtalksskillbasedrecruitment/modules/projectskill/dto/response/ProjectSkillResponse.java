package org.example.techtalksskillbasedrecruitment.modules.projectskill.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProjectSkillResponse {
    private Integer projectSkillId;
    private Integer projectId;
    private String projectName;
}
