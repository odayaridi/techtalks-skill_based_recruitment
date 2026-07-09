package org.example.techtalksskillbasedrecruitment.jobskill;

import jakarta.transaction.Transactional;
import org.example.techtalksskillbasedrecruitment.exceptions.ResourceNotFoundException;
import org.example.techtalksskillbasedrecruitment.job.Job;
import org.example.techtalksskillbasedrecruitment.job.JobRepository;
import org.example.techtalksskillbasedrecruitment.jobskill.dto.request.CreateJobSkill;
import org.example.techtalksskillbasedrecruitment.jobskill.dto.response.JobSkillResponse;
import org.example.techtalksskillbasedrecruitment.skill.Skill;
import org.example.techtalksskillbasedrecruitment.skill.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobSkillService {
    private final JobSkillRepository jobSkillRepository;
    private final JobRepository jobRepository;
    private final SkillRepository skillRepository;
    public JobSkillService(JobSkillRepository jobSkillRepository, JobRepository jobRepository, SkillRepository skillRepository) {
        this.jobSkillRepository = jobSkillRepository;
        this.jobRepository = jobRepository;
        this.skillRepository = skillRepository;
    }

    @Transactional
    public List<JobSkillResponse> createJobSkillsService(List<CreateJobSkill> jobSkills){
        List<JobSkillResponse> jobSkillResponses = new ArrayList<>();
        for (CreateJobSkill jobSkill : jobSkills) {
            Job job = this.jobRepository.findById(jobSkill.getJobId()).orElseThrow(()-> new ResourceNotFoundException("Invalid job id while creating job skill"));
            Skill skill = this.skillRepository.findBySkillName(jobSkill.getSkillName());
            if(skill == null) {
                throw new ResourceNotFoundException("Invalid skill name");
            }
            JobSkill jSkill = new JobSkill();
            jSkill.setSkill(skill);
            jSkill.setJob(job);
            jSkill.setWeight(jobSkill.getWeight());
            JobSkill newJobSkill = this.jobSkillRepository.save(jSkill);
            jobSkillResponses.add(new JobSkillResponse(newJobSkill.getJobSkillId(),newJobSkill.getJob().getJobId(),newJobSkill.getSkill().getSkillName(),newJobSkill.getWeight()));
        }
        return jobSkillResponses;
    }


    public void deleteJobSkillService(Integer jobId, String skillName){
        Job job = this.jobRepository.findById(jobId).orElseThrow(() -> new ResourceNotFoundException("Invalid job id"));
        Skill skill = this.skillRepository.findBySkillName(skillName);
        if (skill == null) {
            throw new ResourceNotFoundException("Invalid Skill name");
        }
        JobSkill toDeleteJobSkill = this.jobSkillRepository.findByJobAndSkill(job,skill);
        this.jobSkillRepository.delete(toDeleteJobSkill);
    }
}
