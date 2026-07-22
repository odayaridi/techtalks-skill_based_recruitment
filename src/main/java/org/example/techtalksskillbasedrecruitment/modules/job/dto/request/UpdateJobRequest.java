package org.example.techtalksskillbasedrecruitment.modules.job.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateJobRequest {
    @NotBlank(message = "Job title is required")
    private String title;

    @NotBlank(message = "Job description is required")
    private String description;

    @NotBlank(message = "Job type is required")
    private String jobType;

    @NotBlank(message = "Job location is required")
    private String location;

    @NotBlank(message = "Job status is required")
    private String status;
}
