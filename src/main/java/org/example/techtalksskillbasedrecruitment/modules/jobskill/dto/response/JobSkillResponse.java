package org.example.techtalksskillbasedrecruitment.modules.jobskill.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class JobSkillResponse {
    private Integer jobSkillId;
    private Integer jobId;
    private String skillName;
    private Integer weight;
}
