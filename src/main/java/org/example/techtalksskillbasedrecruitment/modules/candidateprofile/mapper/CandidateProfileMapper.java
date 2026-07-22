package org.example.techtalksskillbasedrecruitment.modules.candidateprofile.mapper;

import org.example.techtalksskillbasedrecruitment.modules.candidateprofile.CandidateProfile;
import org.example.techtalksskillbasedrecruitment.modules.candidateprofile.dto.response.CandidateProfileResponse;
import org.springframework.stereotype.Component;

@Component
public class CandidateProfileMapper {
    public CandidateProfileResponse toDTOResponse(CandidateProfile candidateProfile){
       return new CandidateProfileResponse(
                candidateProfile.getCandidateId(),
                candidateProfile.getUser().getUserId(),
                candidateProfile.getBio(),
                candidateProfile.getLocation(),
                candidateProfile.getGithubUrl(),
                candidateProfile.getLinkedinUrl());
    }

}
