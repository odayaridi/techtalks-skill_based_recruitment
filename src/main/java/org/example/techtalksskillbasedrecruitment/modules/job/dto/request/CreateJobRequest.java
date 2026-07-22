package org.example.techtalksskillbasedrecruitment.modules.job.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
/*
delete
 */
@Getter
@Setter
public class CreateJobRequest {

    @NotBlank(message = "Company name is required")
    private String companyName;

    @NotNull(message = "Created by user ID is required")
    private Integer userId;

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