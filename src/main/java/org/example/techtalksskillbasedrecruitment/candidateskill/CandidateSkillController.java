package org.example.techtalksskillbasedrecruitment.candidateskill;

import org.example.techtalksskillbasedrecruitment.candidateskill.dto.request.CreateCandidateSkillRequest;
import org.example.techtalksskillbasedrecruitment.candidateskill.dto.response.CandidateSkillResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/candidate-skill")
public class CandidateSkillController {
    private final CandidateSkillService candidateSkillService;

    public CandidateSkillController(CandidateSkillService candidateSkillService) {
        this.candidateSkillService = candidateSkillService;
    }

    @PostMapping("/create")
    public ResponseEntity<CandidateSkillResponse> createCandidateSkillController(@RequestBody CreateCandidateSkillRequest createCandidateSkillRequest) {
        CandidateSkillResponse candidateSkillResponse = this.candidateSkillService.createCandidateSkillService(createCandidateSkillRequest);
        return new ResponseEntity<>(candidateSkillResponse, HttpStatus.CREATED);
    }
}