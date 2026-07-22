package org.example.techtalksskillbasedrecruitment.modules.candidateresume.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class CandidateResumeResponse {

    private Integer resumeId;
    private Integer candidateId;

    private String filePath;
}
