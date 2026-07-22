package org.example.techtalksskillbasedrecruitment.modules.candidateprofile.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCanProfileRequest {

    @NotBlank(message = "User id is required")
    private Integer userId;
    @NotBlank(message = "bio is required")
    private String bio;
    @NotBlank(message = "location is required")
    private String location;
    @NotBlank(message = "Github url is required")
    private String githubUrl;
    @NotBlank(message = "Linked in url is required")
    private String linkedinUrl;

}
