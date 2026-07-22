package org.example.techtalksskillbasedrecruitment.modules.jobskill;


import org.example.techtalksskillbasedrecruitment.modules.job.Job;
import org.example.techtalksskillbasedrecruitment.modules.skill.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobSkillRepository extends JpaRepository<JobSkill,Integer> {
    JobSkill findByJobAndSkill(Job job, Skill skill);
    List<JobSkill> findByJob(Job job);
}
