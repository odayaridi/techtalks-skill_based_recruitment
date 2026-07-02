package org.example.techtalksskillbasedrecruitment.candidateresume.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UpdateProjectRequest {
    @NotBlank(message = "Project name is required")
    private String projectName;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    private String githubUrl;

    private String demoUrl;
}
