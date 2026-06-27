package org.example.techtalksskillbasedrecruitment.Views.Recruiter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecruiterViewService {

    @Autowired
    private RecruiterViewRepository RecRepo;

    public RecruiterView getRecruiterByUserId(Integer id) {
        return RecRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Recruiter not found with id: " + id));

    }
}
