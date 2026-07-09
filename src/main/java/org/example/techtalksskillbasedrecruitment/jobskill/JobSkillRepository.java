package org.example.techtalksskillbasedrecruitment.jobskill;


import org.example.techtalksskillbasedrecruitment.job.Job;
import org.example.techtalksskillbasedrecruitment.skill.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobSkillRepository extends JpaRepository<JobSkill,Integer> {
    JobSkill findByJobAndSkill(Job job, Skill skill);
    List<JobSkill> findByJob(Job job);
}
