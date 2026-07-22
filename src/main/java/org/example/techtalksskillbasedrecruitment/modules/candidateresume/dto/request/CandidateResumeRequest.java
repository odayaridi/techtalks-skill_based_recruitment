package org.example.techtalksskillbasedrecruitment.modules.candidateresume.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CandidateResumeRequest {
    @NotBlank(message = "Candidate id is required")
    private Integer candidateId;
    @NotBlank(message = "File path is required")
    private String filePath;
}

