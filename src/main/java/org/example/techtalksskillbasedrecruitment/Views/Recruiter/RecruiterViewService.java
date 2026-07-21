package org.example.techtalksskillbasedrecruitment.Views.Recruiter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RecruiterViewService {

    @Autowired
    private RecruiterViewRepository recruiterViewRepository;

    public RecruiterView getRecruiterByUserIdService(Integer id) {
        return recruiterViewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recruiter not found with id: " + id));
    }


    public Page<RecruiterView> fetchAllRecruitersService(Pageable pageable) {
        return recruiterViewRepository.findAll(pageable);
    }
}
