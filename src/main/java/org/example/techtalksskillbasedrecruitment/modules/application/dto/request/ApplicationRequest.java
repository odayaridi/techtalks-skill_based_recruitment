package org.example.techtalksskillbasedrecruitment.modules.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationRequest {
    @NotNull(message = "Job id is required")
    private Integer jobId;
    @NotNull(message = "Candidate id is required")
    private Integer candidateId;

}
