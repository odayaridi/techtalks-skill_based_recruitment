package org.example.techtalksskillbasedrecruitment.job.dto.response;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JobResponse {

    private Integer jobId;

    private String companyName;


    private Integer createdBy;


    private String title;


    private String description;

    private String jobType;


    private String location;


    private String status;
}