package org.example.techtalksskillbasedrecruitment.candidateresume;

import jakarta.transaction.Transactional;
import lombok.Setter;
import org.example.techtalksskillbasedrecruitment.candidateprofile.CandidateProfile;
import org.example.techtalksskillbasedrecruitment.candidateprofile.CandidateProfileRepository;
import org.example.techtalksskillbasedrecruitment.candidateresume.dto.request.CandidateResumeRequest;
import org.example.techtalksskillbasedrecruitment.candidateresume.dto.request.UpdateCandidateResumeRequest;
import org.example.techtalksskillbasedrecruitment.candidateresume.dto.response.CandidateResumeResponse;
import org.example.techtalksskillbasedrecruitment.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CandidateResumeService {
    private final CandidateResumeRepo candidateResumeRepo;
    private final CandidateProfileRepository candidateProfileRepository;

    public CandidateResumeService(CandidateResumeRepo candidateResumeRepo, CandidateProfileRepository candidateProfileRepository) {
        this.candidateResumeRepo = candidateResumeRepo;
        this.candidateProfileRepository = candidateProfileRepository;
    }

    public CandidateResumeResponse createCandidateResumeService(CandidateResumeRequest resumeRequest){
        CandidateProfile candidateProfile = this.candidateProfileRepository.findById(resumeRequest.getCandidateId()).
                orElseThrow(() -> new ResourceNotFoundException("User did not create a candidate account yet to upload a resume to him"));
        CandidateResume candidateResume = new CandidateResume();
        candidateResume.setCandidate(candidateProfile);
        candidateResume.setFilePath(resumeRequest.getFilePath());

        CandidateResume newCandidateResume = this.candidateResumeRepo.save(candidateResume);
        return new CandidateResumeResponse(newCandidateResume.getResumeId(),newCandidateResume.getCandidate().getCandidateId(),newCandidateResume.getFilePath());
    }

    @Transactional
    public void deleteCandidateResumeService(Integer resumeId) {
        CandidateResume candidateResume = this.candidateResumeRepo.findById(resumeId).orElseThrow(() -> new ResourceNotFoundException("Resume does not exist to fetch it"));
        this.candidateResumeRepo.delete(candidateResume);
    }

    public CandidateResumeResponse getCandidateResumeService(Integer candidateId) {
        CandidateProfile candidateProfile = this.candidateProfileRepository.findById(candidateId).orElseThrow(() -> new ResourceNotFoundException("Candidate account does not exist to retrieve a resume"));
        CandidateResume candidateResume = this.candidateResumeRepo.findByCandidate(candidateProfile);
        if(candidateResume == null) {
            throw new ResourceNotFoundException("Candidate resume does not exist to retrieve it");
        }
        return new CandidateResumeResponse(candidateResume.getResumeId(),candidateResume.getCandidate().getCandidateId(),candidateResume.getFilePath());
    }
    public CandidateResumeResponse updateCandidateResumeService(UpdateCandidateResumeRequest updateRequest) {
        CandidateResume candidateResume = this.candidateResumeRepo.findById(updateRequest.getResumeId())
                .orElseThrow(() -> new ResourceNotFoundException("Resume with ID " + updateRequest.getResumeId() + " does not exist to update it"));

        candidateResume.setFilePath(updateRequest.getFilePath());

        CandidateResume updatedResume = this.candidateResumeRepo.save(candidateResume);

        return new CandidateResumeResponse(
                updatedResume.getResumeId(),
                updatedResume.getCandidate().getCandidateId(),
                updatedResume.getFilePath()
        );
    }
    public Map<String, Boolean> existsCandidateResumeByCandidateIdService(Integer candidateId) {
        CandidateProfile existingCandidateProfile = this.candidateProfileRepository.findById(candidateId).orElseThrow(() ->
                new ResourceNotFoundException("Candidate is not found to check whether a resume exists for him"));
        boolean existingResumeForCandidate = this.candidateResumeRepo.existsByCandidate(existingCandidateProfile);
        Map<String, Boolean> response = new HashMap<>();
        response.put("resumeExists", existingResumeForCandidate);
        return response;
    }
}
