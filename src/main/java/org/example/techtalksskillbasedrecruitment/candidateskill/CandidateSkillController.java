package org.example.techtalksskillbasedrecruitment.candidateskill;

import org.example.techtalksskillbasedrecruitment.candidateskill.dto.request.UpdateCandidateSkillRequest;
import org.example.techtalksskillbasedrecruitment.candidateskill.dto.response.CandidateSkillResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/candidate-skill")
public class CandidateSkillController {

    private final CandidateSkillService candidateSkillService;

    public CandidateSkillController(CandidateSkillService candidateSkillService) {
        this.candidateSkillService = candidateSkillService;
    }

    @PutMapping("/update/candidateSkillId/{candidateSkillId}")
    public CandidateSkillResponse updateCandidateSkill(
            @PathVariable Integer candidateSkillId,
            @RequestBody UpdateCandidateSkillRequest request) {

        return candidateSkillService.updateCandidateSkill(candidateSkillId, request);
    }
    @DeleteMapping("/delete/candidateSkillId/{candidateSkillId}")
    public ResponseEntity<Void> deleteCandidateSkill(@PathVariable Integer candidateSkillId) {
        candidateSkillService.deleteCandidateService(candidateSkillId);
        return ResponseEntity.noContent().build();
    }
}