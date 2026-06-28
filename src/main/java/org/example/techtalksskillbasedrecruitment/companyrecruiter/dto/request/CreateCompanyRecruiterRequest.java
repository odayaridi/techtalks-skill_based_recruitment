package org.example.techtalksskillbasedrecruitment.companyrecruiter.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCompanyRecruiterRequest {

    @NotBlank(message = "Company name is required")
    private String companyName;

    @NotNull(message = "User id is required")
    private int userId;
}
