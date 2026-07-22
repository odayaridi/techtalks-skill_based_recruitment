package org.example.techtalksskillbasedrecruitment.modules.candidateresume;

import jakarta.transaction.Transactional;
import org.example.techtalksskillbasedrecruitment.modules.candidateprofile.CandidateProfile;
import org.example.techtalksskillbasedrecruitment.modules.candidateprofile.CandidateProfileRepository;
import org.example.techtalksskillbasedrecruitment.modules.candidateresume.dto.request.CandidateResumeRequest;
import org.example.techtalksskillbasedrecruitment.modules.candidateresume.dto.request.UpdateCandidateResumeRequest;
import org.example.techtalksskillbasedrecruitment.modules.candidateresume.dto.response.CandidateResumeResponse;
import org.example.techtalksskillbasedrecruitment.modules.candidateresume.mapper.CandidateResumeMapper;
import org.example.techtalksskillbasedrecruitment.common.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CandidateResumeService {

    private final CandidateResumeRepo candidateResumeRepo;
    private final CandidateProfileRepository candidateProfileRepository;
    private final CandidateResumeMapper candidateResumeMapper;

    public CandidateResumeService(
            CandidateResumeRepo candidateResumeRepo,
            CandidateProfileRepository candidateProfileRepository,
            CandidateResumeMapper candidateResumeMapper
    ) {
        this.candidateResumeRepo = candidateResumeRepo;
        this.candidateProfileRepository = candidateProfileRepository;
        this.candidateResumeMapper = candidateResumeMapper;
    }


    public CandidateResumeResponse createCandidateResumeService(CandidateResumeRequest resumeRequest) {

        CandidateProfile candidateProfile = candidateProfileRepository
                .findById(resumeRequest.getCandidateId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User did not create a candidate account yet to upload a resume"
                        )
                );

        CandidateResume candidateResume = new CandidateResume();
        candidateResume.setCandidate(candidateProfile);
        candidateResume.setFilePath(resumeRequest.getFilePath());

        CandidateResume savedResume = candidateResumeRepo.save(candidateResume);

        return candidateResumeMapper.toCandidateResumeResponseDTO(savedResume);
    }


    @Transactional
    public void deleteCandidateResumeService(Integer resumeId) {

        CandidateResume candidateResume = candidateResumeRepo.findById(resumeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Resume does not exist to delete it"
                        )
                );

        candidateResumeRepo.delete(candidateResume);
    }


    public CandidateResumeResponse getCandidateResumeService(Integer candidateId) {

        CandidateProfile candidateProfile = candidateProfileRepository
                .findById(candidateId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Candidate account does not exist to retrieve resume"
                        )
                );

        CandidateResume candidateResume = candidateResumeRepo.findByCandidate(candidateProfile);

        if (candidateResume == null) {
            throw new ResourceNotFoundException(
                    "Candidate resume does not exist"
            );
        }

        return candidateResumeMapper.toCandidateResumeResponseDTO(candidateResume);
    }


    public CandidateResumeResponse updateCandidateResumeService(
            UpdateCandidateResumeRequest updateRequest
    ) {

        CandidateResume candidateResume = candidateResumeRepo
                .findById(updateRequest.getResumeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Resume with ID "
                                        + updateRequest.getResumeId()
                                        + " does not exist to update"
                        )
                );


        candidateResume.setFilePath(updateRequest.getFilePath());

        CandidateResume updatedResume = candidateResumeRepo.save(candidateResume);

        return candidateResumeMapper.toCandidateResumeResponseDTO(updatedResume);
    }


    public Map<String, Boolean> existsCandidateResumeByCandidateIdService(
            Integer candidateId
    ) {

        CandidateProfile candidateProfile = candidateProfileRepository
                .findById(candidateId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Candidate is not found to check resume existence"
                        )
                );


        boolean exists = candidateResumeRepo.existsByCandidate(candidateProfile);

        Map<String, Boolean> response = new HashMap<>();
        response.put("resumeExists", exists);

        return response;
    }
}