package org.example.techtalksskillbasedrecruitment.Views.Recruiter;

import org.example.techtalksskillbasedrecruitment.common.response.PaginatedResponse;
import org.example.techtalksskillbasedrecruitment.common.response.PaginationMeta;
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


}
