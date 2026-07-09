package org.example.techtalksskillbasedrecruitment.candidateskill.dto.response;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.techtalksskillbasedrecruitment.candidateprofile.CandidateProfile;
import org.example.techtalksskillbasedrecruitment.skill.Skill;

@Getter
@Setter
@AllArgsConstructor
public class CandidateSkillResponse {
    private Integer candidateSkillId;

    private Integer candidateId;

    private String skillName;

    private String level;

    private Integer yearsOfExperience;
}


