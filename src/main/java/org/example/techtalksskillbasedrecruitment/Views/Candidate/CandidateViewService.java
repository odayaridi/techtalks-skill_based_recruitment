package org.example.techtalksskillbasedrecruitment.Views.Candidate;

import org.example.techtalksskillbasedrecruitment.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Page<CandidateView> fetchAllCandidatesService(Pageable pageable) {
        return this.candidateViewRepository.findAll(pageable);
    }

}
