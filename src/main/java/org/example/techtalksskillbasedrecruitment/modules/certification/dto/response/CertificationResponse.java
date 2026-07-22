package org.example.techtalksskillbasedrecruitment.modules.certification.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class CertificationResponse {
    private Integer certificateId;

    private Integer candidateId;

    private String certificateName;

    private String issuedBy;


    private String certificateFile;
}
