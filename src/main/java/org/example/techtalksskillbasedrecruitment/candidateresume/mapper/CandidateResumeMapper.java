package org.example.techtalksskillbasedrecruitment.candidateresume.mapper;

import org.example.techtalksskillbasedrecruitment.candidateresume.CandidateResume;
import org.example.techtalksskillbasedrecruitment.candidateresume.dto.response.CandidateResumeResponse;
import org.springframework.stereotype.Component;

@Component
public class CandidateResumeMapper {
    public CandidateResumeResponse toCandidateResumeResponseDTO(
            CandidateResume candidateResume){
            return new CandidateResumeResponse(candidateResume.getResumeId(),
                candidateResume.getCandidate().getCandidateId(),
                candidateResume.getFilePath());
    }
    }

