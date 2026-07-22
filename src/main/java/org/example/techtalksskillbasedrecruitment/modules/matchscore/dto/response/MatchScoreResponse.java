package org.example.techtalksskillbasedrecruitment.modules.matchscore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class MatchScoreResponse {
    private Integer candidateId;
    private String candidateName;
    private BigDecimal matchPercentage;
}
