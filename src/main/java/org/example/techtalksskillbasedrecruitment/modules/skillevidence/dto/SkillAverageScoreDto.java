package org.example.techtalksskillbasedrecruitment.modules.skillevidence.dto;

import java.math.BigDecimal;

public class SkillAverageScoreDto {

    private Integer skillId;
    private BigDecimal averageScore;

    public SkillAverageScoreDto(Integer skillId, Double averageScore) {
        this.skillId = skillId;
        this.averageScore = BigDecimal.valueOf(averageScore);
    }

    public Integer getSkillId() {
        return skillId;
    }

    public BigDecimal getAverageScore() {
        return averageScore;
    }
}