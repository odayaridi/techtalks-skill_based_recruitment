package org.example.techtalksskillbasedrecruitment.jobskill;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobSkillRepository extends JpaRepository<JobSkill,Integer> {
    List<JobSkill> findByJob_JobId(Integer jobId);
}
