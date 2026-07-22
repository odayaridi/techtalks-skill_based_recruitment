package org.example.techtalksskillbasedrecruitment.modules.Views.Candidate;

import org.example.techtalksskillbasedrecruitment.common.pagination.PaginatedResponse;
import org.example.techtalksskillbasedrecruitment.common.pagination.PaginationMeta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    @GetMapping("/fetchAllCandidates")
    public ResponseEntity<PaginatedResponse<CandidateView>> fetchAllCandidatesController(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ) {

        Pageable pageable = PageRequest.of(page, limit);

        Page<CandidateView> candidatePage = candidateViewService.fetchAllCandidatesService(pageable);

        PaginationMeta meta = new PaginationMeta(
                candidatePage.getNumber(),
                candidatePage.getSize(),
                candidatePage.getTotalElements(),
                candidatePage.getTotalPages(),
                candidatePage.isFirst(),
                candidatePage.isLast()
        );

        PaginatedResponse<CandidateView> response = new PaginatedResponse<>(
                candidatePage.getContent(),
                meta
        );

        return ResponseEntity.ok(response);
    }


}