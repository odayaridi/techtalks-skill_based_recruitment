package org.example.techtalksskillbasedrecruitment.application;

import org.example.techtalksskillbasedrecruitment.jobskill.JobSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application,Integer> {
    List<Application> findByJob_JobId(Integer jobId);
}
