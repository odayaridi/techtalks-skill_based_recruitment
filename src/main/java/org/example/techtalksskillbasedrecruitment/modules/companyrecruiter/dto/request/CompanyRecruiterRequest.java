package org.example.techtalksskillbasedrecruitment.modules.companyrecruiter.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyRecruiterRequest {

    @NotBlank(message = "Company name is required")
    private String companyName;

    @NotNull(message = "User id is required")
    private int userId;
}
