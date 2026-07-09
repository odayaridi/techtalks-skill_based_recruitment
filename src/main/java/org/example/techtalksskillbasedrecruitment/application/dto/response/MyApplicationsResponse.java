package org.example.techtalksskillbasedrecruitment.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class MyApplicationsResponse {
    private Integer applicationId;
    private String applicationStatus;
    private String JobTitle;
    private String jobDescription;
    private String jobType;
    private String jobLocation;
    private String companyName;
    private String companyLocation;
    private String logoPath;
}
