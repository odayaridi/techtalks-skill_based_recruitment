
package org.example.techtalksskillbasedrecruitment.modules.Views.Recruiter;

import org.example.techtalksskillbasedrecruitment.common.pagination.PaginatedResponse;
import org.example.techtalksskillbasedrecruitment.common.pagination.PaginationMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recruiter-view")

public class RecruiterViewController {

    @Autowired
    private RecruiterViewService recruiterViewService;

    @GetMapping("/{id}")
    public RecruiterView getRecruiterByUserIdController(@PathVariable Integer id){
        return recruiterViewService.getRecruiterByUserIdService(id);
    }

    @GetMapping("/fetchAllRecruiters")
    public ResponseEntity<PaginatedResponse<RecruiterView>> fetchAllRecruiters(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ) {

        Pageable pageable = PageRequest.of(page, limit);

        Page<RecruiterView> recruiterPage =
                recruiterViewService.fetchAllRecruitersService(pageable);

        PaginationMeta meta = new PaginationMeta(
                recruiterPage.getNumber(),
                recruiterPage.getSize(),
                recruiterPage.getTotalElements(),
                recruiterPage.getTotalPages(),
                recruiterPage.isFirst(),
                recruiterPage.isLast()
        );

        PaginatedResponse<RecruiterView> response = new PaginatedResponse<>(
                recruiterPage.getContent(),
                meta
        );

        return ResponseEntity.ok(response);
    }

}


