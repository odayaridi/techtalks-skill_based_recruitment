package org.example.techtalksskillbasedrecruitment.Views.Recruiter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.GetMapping;

public interface RecruiterViewRepository
        extends JpaRepository<RecruiterView, Integer> {
}