package org.example.techtalksskillbasedrecruitment.ai.dto.response;

public class DetectedSkill {
    private String skill;
    private Integer score;

    public DetectedSkill() {
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
