package org.example.techtalksskillbasedrecruitment.projectskill.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Createprojectskill {
    @NotNull(message = "project ID is required")
    private Integer projectID;

    @NotBlank(message = "skill name is required")

    private String skillname;

}
