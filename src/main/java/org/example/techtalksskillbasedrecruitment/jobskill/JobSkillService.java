package org.example.techtalksskillbasedrecruitment.jobskill;

import jakarta.transaction.Transactional;
import org.example.techtalksskillbasedrecruitment.exceptions.ResourceNotFoundException;
import org.example.techtalksskillbasedrecruitment.job.Job;
import org.example.techtalksskillbasedrecruitment.job.JobRepository;
import org.example.techtalksskillbasedrecruitment.skill.Skill;
import org.example.techtalksskillbasedrecruitment.skill.SkillRepository;
import org.springframework.stereotype.Service;

@Service
public class JobSkillService {

    private final JobRepository jobRepository;
    private final SkillRepository skillRepository;
    private final JobSkillRepository jobSkillRepository;

    public JobSkillService(JobRepository jobRepository,
                           SkillRepository skillRepository,
                           JobSkillRepository jobSkillRepository) {
        this.jobRepository = jobRepository;
        this.skillRepository = skillRepository;
        this.jobSkillRepository = jobSkillRepository;
    }

    @Transactional
    public void deleteJobSkillService(Integer jobId, String skillName) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid job id"));

        Skill skill = skillRepository.findBySkillName(skillName);
        if (skill == null) {
            throw new ResourceNotFoundException("Invalid Skill name");
        }

        JobSkill jobSkill = jobSkillRepository.findByJobAndSkill(job, skill);
        if (jobSkill == null) {
            throw new ResourceNotFoundException("Job skill relation not found");
        }

        jobSkillRepository.delete(jobSkill);
    }
}