package org.example.techtalksskillbasedrecruitment.Views.Recruiter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recruiters")

public class RecruiterViewController {

    @Autowired
    private RecruiterViewService RecServ;

    @GetMapping("/{id}")
    public RecruiterView getRecruiterByUserId(@PathVariable Integer id){
        return RecServ.getRecruiterByUserId(id);
    }
}
