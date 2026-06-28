package org.example.techtalksskillbasedrecruitment.Views.Candidate;

import org.example.techtalksskillbasedrecruitment.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CandidateViewService {
    private final CandidateViewRepository candidateViewRepository;

    public CandidateViewService(CandidateViewRepository candidateViewRepository) {
        this.candidateViewRepository = candidateViewRepository;
    }

    public CandidateView getCandidateByUserIdService(Integer userId) {
        return this.candidateViewRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate is not found"));
    }
}
