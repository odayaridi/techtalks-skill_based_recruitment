package org.example.techtalksskillbasedrecruitment.modules.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class CandidateAppsResponse {
    private Integer applicationId;
    private String status;
    private LocalDateTime appliedAt;
    private String location;
    private String bio;
    private String linkedinUrl;
    private String githubUrl;
    private String username;
    private String email;
    private String phoneNumber;
}