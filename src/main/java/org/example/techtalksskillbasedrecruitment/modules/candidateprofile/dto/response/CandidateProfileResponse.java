package org.example.techtalksskillbasedrecruitment.modules.candidateprofile.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CandidateProfileResponse {

    private Integer candidateId;

    private Integer userId;

    private String bio;

    private String location;

    private String githubUrl;

    private String linkedinUrl;

}
