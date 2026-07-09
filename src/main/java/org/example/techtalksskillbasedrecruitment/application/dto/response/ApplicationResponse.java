package org.example.techtalksskillbasedrecruitment.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ApplicationResponse {
    private Integer applicationId;
    private Integer jobId;
    private Integer candidateId;
    private String status;

}
