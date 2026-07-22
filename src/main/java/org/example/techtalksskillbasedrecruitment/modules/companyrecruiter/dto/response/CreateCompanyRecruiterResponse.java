package org.example.techtalksskillbasedrecruitment.modules.companyrecruiter.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CreateCompanyRecruiterResponse {
    private Integer companyRecruiterId;
    private Integer companyId;
    private Integer userId;
}
