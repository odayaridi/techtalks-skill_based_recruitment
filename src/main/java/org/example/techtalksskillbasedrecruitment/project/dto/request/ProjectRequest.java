package org.example.techtalksskillbasedrecruitment.project.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProjectRequest {
    @NotNull(message = "Candidate id is required")
    private Integer candidateId;

    @NotBlank(message = "Project name is required")
    private String projectName;

    private String description;

    private LocalDate startDate;

    private LocalDate endDate;

    private String githubUrl;

    private String demoUrl;
}