package org.example.techtalksskillbasedrecruitment.modules.job;

import jakarta.transaction.Transactional;
import org.example.techtalksskillbasedrecruitment.modules.application.Application;
import org.example.techtalksskillbasedrecruitment.modules.application.ApplicationRepository;
import org.example.techtalksskillbasedrecruitment.modules.candidateprofile.CandidateProfile;
import org.example.techtalksskillbasedrecruitment.modules.candidateskillscore.CandidateSkillScore;
import org.example.techtalksskillbasedrecruitment.modules.candidateskillscore.CandidateSkillScoreRepository;
import org.example.techtalksskillbasedrecruitment.modules.company.Company;
import org.example.techtalksskillbasedrecruitment.modules.company.CompanyRepository;
import org.example.techtalksskillbasedrecruitment.common.exceptions.ResourceNotFoundException;
import org.example.techtalksskillbasedrecruitment.modules.job.dto.request.CreateJobRequest;
import org.example.techtalksskillbasedrecruitment.modules.job.dto.request.UpdateJobRequest;
import org.example.techtalksskillbasedrecruitment.modules.job.dto.response.JobResponse;
import org.example.techtalksskillbasedrecruitment.modules.job.mapper.JobMapper;
import org.example.techtalksskillbasedrecruitment.modules.jobskill.JobSkill;
import org.example.techtalksskillbasedrecruitment.modules.jobskill.JobSkillRepository;
import org.example.techtalksskillbasedrecruitment.modules.matchscore.MatchScore;
import org.example.techtalksskillbasedrecruitment.modules.matchscore.MatchScoreRepository;
import org.example.techtalksskillbasedrecruitment.modules.matchscore.dto.response.MatchScoreResponse;
import org.example.techtalksskillbasedrecruitment.modules.user.User;
import org.example.techtalksskillbasedrecruitment.modules.user.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobService {
    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final JobSkillRepository jobSkillRepository;
    private final ApplicationRepository applicationRepository;
    private final CandidateSkillScoreRepository candidateSkillScoreRepository;
    private final MatchScoreRepository matchScoreRepository;
    private final JobMapper jobMapper;

    public JobService(JobRepository jobRepository, CompanyRepository companyRepository, UserRepository userRepository, JobSkillRepository jobSkillRepository, ApplicationRepository applicationRepository, CandidateSkillScoreRepository candidateSkillScoreRepository, MatchScoreRepository matchScoreRepository, JobMapper jobMapper) {
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
        this.jobSkillRepository = jobSkillRepository;
        this.applicationRepository = applicationRepository;
        this.candidateSkillScoreRepository = candidateSkillScoreRepository;
        this.matchScoreRepository = matchScoreRepository;
        this.jobMapper = jobMapper;
    }


    public JobResponse postJobService(CreateJobRequest jobRequest) {

        Company company = this.companyRepository.findByCompanyName(jobRequest.getCompanyName());

        if (company == null) {
            throw new ResourceNotFoundException(
                    "Company is not found with this invalid company name"
            );
        }


        User existingUser = this.userRepository.findById(jobRequest.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User is not found with this user id"
                        )
                );


        Job job = new Job();

        job.setCompany(company);
        job.setJobType(jobRequest.getJobType());
        job.setCreatedBy(existingUser);
        job.setDescription(jobRequest.getDescription());
        job.setLocation(jobRequest.getLocation());
        job.setTitle(jobRequest.getTitle());
        job.setStatus(jobRequest.getStatus());


        Job newJob = this.jobRepository.save(job);

        return this.jobMapper.toJobResponseDTO(newJob);
    }


    public List<JobResponse> getJobsByCompanyNameService(String companyName) {

        Company company = this.companyRepository.findByCompanyName(companyName);

        if (company == null) {
            throw new ResourceNotFoundException(
                    "No company exists with this name to get the posted jobs"
            );
        }


        List<Job> jobsPostedByCompany = this.jobRepository.findByCompany(company);

        return getJobResponses(jobsPostedByCompany);
    }


    public JobResponse updateJobDetailsService(
            Integer jobId,
            UpdateJobRequest jobRequest
    ) {

        Job existingJob = this.jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Job does not exist with the id to update its content"
                        )
                );


        existingJob.setStatus(jobRequest.getStatus());
        existingJob.setJobType(jobRequest.getJobType());
        existingJob.setTitle(jobRequest.getTitle());
        existingJob.setLocation(jobRequest.getLocation());
        existingJob.setDescription(jobRequest.getDescription());


        Job updatedJob = this.jobRepository.save(existingJob);

        return this.jobMapper.toJobResponseDTO(updatedJob);
    }


    private List<JobResponse> getJobResponses(List<Job> jobsPostedByCompany) {

        List<JobResponse> jobsResponses = new ArrayList<>();

        for (Job job : jobsPostedByCompany) {

            JobResponse jobsResponse = this.jobMapper.toJobResponseDTO(job);

            jobsResponses.add(jobsResponse);
        }

        return jobsResponses;
    }


    public void deleteJobService(Integer jobId) {

        Job job = this.jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Job does not exist to delete it. Invalid job id."
                        )
                );

        this.jobRepository.delete(job);
    }


    public List<JobResponse> getPostedJobsByRecruiterService(Integer recruiterId) {

        User user = this.userRepository.findById(recruiterId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Invalid Recruiter id. Cannot fetch the posted jobs"
                        )
                );


        List<Job> jobsList = this.jobRepository.findByCreatedBy(user);


        return jobsList.stream()
                .map(this.jobMapper::toJobResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<MatchScoreResponse> calculateMatchScoresService(Integer jobId) {

        Job job = this.jobRepository.findById(jobId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Invalid Job Id. Cannot calculate match scores."));

        List<JobSkill> jobSkillList = this.jobSkillRepository.findByJob(job);

        List<Application> applicationList =
                this.applicationRepository.findByJob(job);

        List<MatchScore> matchScores = new ArrayList<>();

        for (Application application : applicationList) {

            CandidateProfile candidate = application.getCandidate();

            List<CandidateSkillScore> candidateSkills =
                    this.candidateSkillScoreRepository.findByCandidate(candidate);

            BigDecimal totalContribution = BigDecimal.ZERO;

            for (JobSkill jobSkill : jobSkillList) {

                CandidateSkillScore candidateSkill = null;

                for (CandidateSkillScore css : candidateSkills) {

                    if (css.getSkill().getSkillId()
                            .equals(jobSkill.getSkill().getSkillId())) {

                        candidateSkill = css;
                        break;
                    }
                }

                BigDecimal contribution = BigDecimal.ZERO;

                if (candidateSkill != null) {

                    BigDecimal candidateScore = candidateSkill.getScore();

                    BigDecimal requiredScore =
                            BigDecimal.valueOf(jobSkill.getWeight());

                    if (candidateScore.compareTo(requiredScore) >= 0) {

                        contribution = BigDecimal.valueOf(100);

                    } else {

                        contribution = candidateScore
                                .multiply(BigDecimal.valueOf(100))
                                .divide(requiredScore, 2, RoundingMode.HALF_UP);
                    }
                }

                totalContribution =
                        totalContribution.add(contribution);
            }

            BigDecimal compatibility =
                    totalContribution.divide(
                            BigDecimal.valueOf(jobSkillList.size()),
                            2,
                            RoundingMode.HALF_UP
                    );

            MatchScore matchScore = new MatchScore();
            matchScore.setCandidate(candidate);
            matchScore.setJob(job);
            matchScore.setMatchPercentage(compatibility);

            MatchScore saved =
                    this.matchScoreRepository.save(matchScore);

            matchScores.add(saved);
        }

        return matchScores.stream()
                .sorted(
                        Comparator.comparing(MatchScore::getMatchPercentage)
                                .reversed())
                .map(matchScore ->
                        new MatchScoreResponse(
                                matchScore.getCandidate().getCandidateId(),
                                matchScore.getCandidate().getUser().getUsername(),
                                matchScore.getMatchPercentage()
                        ))
                .toList();
    }

}
