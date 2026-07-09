package org.example.techtalksskillbasedrecruitment.candidateresume;


import lombok.Getter;
import org.example.techtalksskillbasedrecruitment.candidateresume.dto.request.CandidateResumeRequest;
import org.example.techtalksskillbasedrecruitment.candidateresume.dto.request.UpdateCandidateResumeRequest;
import org.example.techtalksskillbasedrecruitment.candidateresume.dto.response.CandidateResumeResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*", allowCredentials = "false")
@RestController
@RequestMapping("/api/candidate-resume")
public class CandidateResumeController {
    private final CandidateResumeService candidateResumeService;

    public CandidateResumeController(CandidateResumeService candidateResumeService) {
        this.candidateResumeService = candidateResumeService;
    }

    @PostMapping("/create")
    public ResponseEntity<CandidateResumeResponse> createCandidateResumeController(@RequestBody CandidateResumeRequest candidateResumeRequest) {
        CandidateResumeResponse candidateResumeResponse = this.candidateResumeService.createCandidateResumeService(candidateResumeRequest);
        return new ResponseEntity<CandidateResumeResponse>(candidateResumeResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/resumeId/{resumeId}")
    public ResponseEntity<Void> deleteCandidateResumeController(@PathVariable Integer resumeId){
        this.candidateResumeService.deleteCandidateResumeService(resumeId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/candidateId/{candidateId}")
    public ResponseEntity<CandidateResumeResponse> getCandidateResumeController(@PathVariable Integer candidateId) {
        CandidateResumeResponse candidateResumeResponse = this.candidateResumeService.getCandidateResumeService(candidateId);
        return new ResponseEntity<CandidateResumeResponse>(candidateResumeResponse,HttpStatus.OK);
    }
    @PutMapping("/update")
    public ResponseEntity<CandidateResumeResponse> updateCandidateResumeController(@RequestBody UpdateCandidateResumeRequest updateCandidateResumeRequest) {
        CandidateResumeResponse candidateResumeResponse = this.candidateResumeService.updateCandidateResumeService(updateCandidateResumeRequest);
        return new ResponseEntity<CandidateResumeResponse>(candidateResumeResponse, HttpStatus.OK);
    }
    @GetMapping("exists/candidateId/{candidateId}")
    public ResponseEntity<Map<String, Boolean>> existsCandidateResumeByCandidateIdController(@PathVariable Integer candidateId) {
        Map<String, Boolean> response = this.candidateResumeService.existsCandidateResumeByCandidateIdService(candidateId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
