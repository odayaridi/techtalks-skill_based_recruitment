package org.example.techtalksskillbasedrecruitment.project.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UpdateProjectRequest {
    @NotBlank(message = "Project name is required")
    private String projectName;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Start Date is required")
    private LocalDate startDate;

    @NotNull(message = "End Date is required")
    private LocalDate endDate;

    @NotBlank(message = "Github Url is required")
    private String githubUrl;

    @NotBlank(message = "Demo url is required")
    private String demoUrl;
}
