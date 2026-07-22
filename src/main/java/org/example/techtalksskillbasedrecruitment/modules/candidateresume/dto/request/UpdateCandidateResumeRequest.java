package org.example.techtalksskillbasedrecruitment.modules.candidateresume.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCandidateResumeRequest {
    @NotNull(message = "Resume id is required")
    private Integer resumeId;

    @NotBlank(message = "File path is required")
    private String filePath;
}
