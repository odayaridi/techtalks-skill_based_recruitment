package org.example.techtalksskillbasedrecruitment.modules.jobskill;

import jakarta.transaction.Transactional;
import org.example.techtalksskillbasedrecruitment.common.exceptions.ResourceNotFoundException;
import org.example.techtalksskillbasedrecruitment.modules.job.Job;
import org.example.techtalksskillbasedrecruitment.modules.job.JobRepository;
import org.example.techtalksskillbasedrecruitment.modules.jobskill.dto.request.CreateJobSkill;
import org.example.techtalksskillbasedrecruitment.modules.jobskill.dto.response.JobSkillResponse;
import org.example.techtalksskillbasedrecruitment.modules.skill.Skill;
import org.example.techtalksskillbasedrecruitment.modules.skill.SkillRepository;
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

    public List<JobSkillResponse> getAllJobSkillsService(Integer jobId){
        Job job = this.jobRepository.findById(jobId).orElseThrow(()->new ResourceNotFoundException("Invalid job id. Cannot retrieve all job skills"));
        List<JobSkill> jobSkillList = this.jobSkillRepository.findByJob(job);
        List<JobSkillResponse> jobSkillResponseList = new ArrayList<>();
        for (JobSkill jobSkill : jobSkillList){
            jobSkillResponseList.add(new JobSkillResponse(jobSkill.getJobSkillId(),jobSkill.getJob().getJobId(),jobSkill.getSkill().getSkillName(),jobSkill.getWeight()));
        }
        return jobSkillResponseList;
    }
}
