package org.example.techtalksskillbasedrecruitment.candidateprofile;


import org.example.techtalksskillbasedrecruitment.candidateprofile.dto.request.CreateCanProfileRequest;
import org.example.techtalksskillbasedrecruitment.candidateprofile.dto.response.CandidateProfileResponse;
import org.example.techtalksskillbasedrecruitment.exceptions.ResourceNotFoundException;
import org.example.techtalksskillbasedrecruitment.user.User;
import org.example.techtalksskillbasedrecruitment.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CandidateProfileService {
    private final CandidateProfileRepository candidateProfileRepository;
    private final UserRepository userRepository;
    public CandidateProfileService(CandidateProfileRepository candidateProfileRepository, UserRepository userRepository) {
        this.candidateProfileRepository = candidateProfileRepository;
        this.userRepository = userRepository;
    }

    public CandidateProfileResponse createCandidateProfileService(CreateCanProfileRequest profile){
         User user = this.userRepository.findById(profile.getUserId()).orElseThrow(() ->
         new ResourceNotFoundException("User is not found by id to create a profile for him as candidate"));

        CandidateProfile candidateProfile = new CandidateProfile();
        candidateProfile.setBio(profile.getBio());
        candidateProfile.setGithubUrl(profile.getGithubUrl());
        candidateProfile.setLocation(profile.getLocation());
        candidateProfile.setUser(user);
        candidateProfile.setLinkedinUrl(profile.getLinkedinUrl());

        CandidateProfile newProfile = candidateProfileRepository.save(candidateProfile);
        return new CandidateProfileResponse(newProfile.getCandidateId(),newProfile.getUser().getUserId(),newProfile.getBio()
                ,newProfile.getLocation(),newProfile.getGithubUrl(),newProfile.getLinkedinUrl());

    }

    public Map<String,Boolean> checkUserHasCandidateService(Integer userId) {
        User existingUser = this.userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("User is not found to check whether a candidate profile exists for him"));
        boolean existingCandidateForUser = this.candidateProfileRepository.existsByUser(existingUser);
        Map <String,Boolean> response = new HashMap<>();
        response.put("candidateExists",existingCandidateForUser);
        return response;
    }

    public CandidateProfileResponse getCandidateProfileService(Integer userId) {
        User existingUser = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User is not found to fetch his candidate profile"));
        CandidateProfile candidateProfile = this.candidateProfileRepository.findByUser(existingUser);
        if (candidateProfile == null) {
            throw new ResourceNotFoundException("Candidate profile for this user is not found");
        }
        return new CandidateProfileResponse(candidateProfile.getCandidateId(),candidateProfile.getUser().getUserId(),candidateProfile.getBio()
                ,candidateProfile.getLocation(),candidateProfile.getGithubUrl(),candidateProfile.getLinkedinUrl());
    }


}
