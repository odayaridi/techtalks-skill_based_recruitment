package org.example.techtalksskillbasedrecruitment.candidateresume;

import jakarta.transaction.Transactional;
import lombok.Setter;
import org.example.techtalksskillbasedrecruitment.candidateprofile.CandidateProfile;
import org.example.techtalksskillbasedrecruitment.candidateprofile.CandidateProfileRepository;
import org.example.techtalksskillbasedrecruitment.candidateresume.dto.request.CandidateResumeRequest;
import org.example.techtalksskillbasedrecruitment.candidateresume.dto.request.UpdateCandidateResumeRequest;
import org.example.techtalksskillbasedrecruitment.candidateresume.dto.response.CandidateResumeResponse;
import org.example.techtalksskillbasedrecruitment.candidateresume.mapper.CandidateResumeMapper;
import org.example.techtalksskillbasedrecruitment.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CandidateResumeService {
    private final CandidateResumeRepo candidateResumeRepo;
    private final CandidateProfileRepository candidateProfileRepository;
   private  final CandidateResumeMapper candidateResumeMapper;
    public CandidateResumeService(CandidateResumeRepo candidateResumeRepo,
                                  CandidateProfileRepository candidateProfileRepository,
                                  CandidateResumeMapper candidateResumeMapper) {

        this.candidateResumeRepo = candidateResumeRepo;
        this.candidateProfileRepository = candidateProfileRepository;
        this.candidateResumeMapper=candidateResumeMapper;
    }

    public CandidateResumeResponse createCandidateResumeService(CandidateResumeRequest resumeRequest){
        CandidateProfile candidateProfile = this.candidateProfileRepository.findById(resumeRequest.getCandidateId()).
                orElseThrow(() -> new ResourceNotFoundException("User did not create a candidate account yet to upload a resume to him"));
        CandidateResume candidateResume = new CandidateResume();
        candidateResume.setCandidate(candidateProfile);
        candidateResume.setFilePath(resumeRequest.getFilePath());

        CandidateResume newCandidateResume = this.candidateResumeRepo.save(candidateResume);
        return candidateResumeMapper.toCandidateResumeResponseDTO(newCandidateResume);
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
        return candidateResumeMapper.toCandidateResumeResponseDTO(candidateResume);
    }
    public CandidateResumeResponse updateCandidateResumeService(UpdateCandidateResumeRequest updateRequest) {
        CandidateResume candidateResume = this.candidateResumeRepo.findById(updateRequest.getResumeId())
                .orElseThrow(() -> new ResourceNotFoundException("Resume with ID " + updateRequest.getResumeId() + " does not exist to update it"));

        candidateResume.setFilePath(updateRequest.getFilePath());

        CandidateResume updatedResume = this.candidateResumeRepo.save(candidateResume);

        return candidateResumeMapper.toCandidateResumeResponseDTO(updatedResume);
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
