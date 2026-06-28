package org.example.techtalksskillbasedrecruitment.Views.Candidate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowCredentials = "false")
@RestController
@RequestMapping("/api/candidate-view")
public class CandidateViewController {

    private final CandidateViewService candidateViewService;

    public CandidateViewController(CandidateViewService candidateViewService) {
        this.candidateViewService = candidateViewService;
    }

    @GetMapping("/getCandidateByUserId/{userId}")
    public ResponseEntity<CandidateView> getCandidateByUserIdController(@PathVariable Integer userId) {
        CandidateView candidateView = this.candidateViewService.getCandidateByUserIdService(userId);

        return new ResponseEntity<>(candidateView, HttpStatus.OK);
    }
}