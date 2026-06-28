package org.example.techtalksskillbasedrecruitment.job;

import org.example.techtalksskillbasedrecruitment.company.Company;
import org.example.techtalksskillbasedrecruitment.company.CompanyRepository;
import org.example.techtalksskillbasedrecruitment.exceptions.ResourceNotFoundException;
import org.example.techtalksskillbasedrecruitment.job.dto.request.CreateJobRequest;
import org.example.techtalksskillbasedrecruitment.job.dto.request.UpdateJobRequest;
import org.example.techtalksskillbasedrecruitment.job.dto.response.JobResponse;
import org.example.techtalksskillbasedrecruitment.user.User;
import org.example.techtalksskillbasedrecruitment.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobService {
    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    public JobService(JobRepository jobRepository, CompanyRepository companyRepository, UserRepository userRepository) {
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }


    public JobResponse postJobService(CreateJobRequest jobRequest){
        Company company = this.companyRepository.findByCompanyName(jobRequest.getCompanyName());
        if(company == null) {
            throw new ResourceNotFoundException("Company is not found with this invalid company name");
        }
        User existingUser = this.userRepository.findById(jobRequest.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User is not found with this user id"));
        Job job = new Job();
        job.setCompany(company);
        job.setJobType(jobRequest.getJobType());
        job.setCreatedBy(existingUser);
        job.setDescription(jobRequest.getDescription());
        job.setLocation(jobRequest.getLocation());
        job.setTitle(jobRequest.getTitle());
        job.setStatus(jobRequest.getStatus());

        Job newJob = this.jobRepository.save(job);
        return new JobResponse(newJob.getJobId(),newJob.getCompany().getCompanyName(),
                newJob.getCreatedBy().getUserId(), newJob.getTitle(), newJob.getDescription(),
                newJob.getJobType(), newJob.getLocation(), newJob.getStatus());
    }

    public List<JobResponse> getJobsByCompanyNameService(String companyName) {
        Company company = this.companyRepository.findByCompanyName(companyName);

        if (company == null) {
            throw new ResourceNotFoundException(
                    "No company exists with this name to get the posted jobs");
        }

        List<Job> jobsPostedByCompany = this.jobRepository.findByCompany(company);
        return  getJobResponses(jobsPostedByCompany);
    }

    public JobResponse updateJobDetailsService(Integer jobId, UpdateJobRequest jobRequest) {
        Job existingJob = this.jobRepository.findById(jobId).orElseThrow(() -> new ResourceNotFoundException("Job does not exist with the id to update its content"));
        existingJob.setStatus(jobRequest.getStatus());
        existingJob.setJobType(jobRequest.getJobType());
        existingJob.setTitle(jobRequest.getTitle());
        existingJob.setLocation(jobRequest.getLocation());
        existingJob.setDescription(jobRequest.getDescription());
        Job updatedJob = this.jobRepository.save(existingJob);
        return new JobResponse(updatedJob.getJobId(),updatedJob.getCompany().getCompanyName(),
                updatedJob.getCreatedBy().getUserId(), updatedJob.getTitle(), updatedJob.getDescription(),
                updatedJob.getJobType(), updatedJob.getLocation(), updatedJob.getStatus());
    }

    private static List<JobResponse> getJobResponses(List<Job> jobsPostedByCompany) {
        List<JobResponse> jobsResponses = new ArrayList<>();

        for (Job job : jobsPostedByCompany) {
            JobResponse jobsResponse = new JobResponse(
                    job.getJobId(),
                    job.getCompany().getCompanyName(),
                    job.getCreatedBy().getUserId(),
                    job.getTitle(),
                    job.getDescription(),
                    job.getJobType(),
                    job.getLocation(),
                    job.getStatus()
            );

            jobsResponses.add(jobsResponse);
        }
        return jobsResponses;
    }
}
