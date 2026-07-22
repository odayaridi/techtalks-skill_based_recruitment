package org.example.techtalksskillbasedrecruitment.modules.GroqSkillAnalyzer.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExtractedSkillDTO {

    private String skillName;
    private Integer score;
}