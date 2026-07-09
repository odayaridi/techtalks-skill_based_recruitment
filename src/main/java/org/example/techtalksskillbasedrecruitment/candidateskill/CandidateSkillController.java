package org.example.techtalksskillbasedrecruitment.candidateskill;

import org.example.techtalksskillbasedrecruitment.candidateskill.dto.request.CreateCandidateSkillRequest;
import org.example.techtalksskillbasedrecruitment.candidateskill.dto.request.UpdateCandidateSkillRequest;
import org.example.techtalksskillbasedrecruitment.candidateskill.dto.response.CandidateSkillResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowCredentials = "false")
@RestController
@RequestMapping("/api/candidate-skill")
public class CandidateSkillController {
    private final CandidateSkillService candidateSkillService;

    public CandidateSkillController(CandidateSkillService candidateSkillService) {
        this.candidateSkillService = candidateSkillService;
    }

    @PostMapping("/create")
    public ResponseEntity<CandidateSkillResponse> addCandidateSkillController(@RequestBody CreateCandidateSkillRequest createCandidateSkillRequest){
        CandidateSkillResponse candidateSkillResponse = this.candidateSkillService.addCandidateSkillService(createCandidateSkillRequest);
        return ResponseEntity.ok(candidateSkillResponse);
    }

    @GetMapping("/getAll/candidateId/{candidateId}")
    public ResponseEntity<List<CandidateSkillResponse>> getCandidateSkillsController(@PathVariable Integer candidateId){
        List<CandidateSkillResponse> candidateSkillResponses = this.candidateSkillService.getCandidateSkillsService(candidateId);
        return ResponseEntity.ok(candidateSkillResponses);
    }

    @PutMapping("/update/candidateSkillId/{candidateSkillId}")
    public ResponseEntity<CandidateSkillResponse> updateCandidateSkillController(@PathVariable Integer candidateSkillId, @RequestBody UpdateCandidateSkillRequest candidateSkillRequest){
        CandidateSkillResponse candidateSkillResponse = this.candidateSkillService.updateCandidateSkillService(candidateSkillId,candidateSkillRequest);
        return ResponseEntity.ok(candidateSkillResponse);
    }

    @DeleteMapping("/delete/candidateSkillId/{candidateSkillId}")
    public ResponseEntity<Void> deleteCandidateSkillController(@PathVariable Integer candidateSkillId){
        this.candidateSkillService.deleteCandidateService(candidateSkillId);
        return ResponseEntity.noContent().build();
    }
}
