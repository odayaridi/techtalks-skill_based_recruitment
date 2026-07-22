package org.example.techtalksskillbasedrecruitment.modules.application;

import org.example.techtalksskillbasedrecruitment.modules.application.dto.request.ApplicationRequest;
import org.example.techtalksskillbasedrecruitment.modules.application.dto.response.ApplicationResponse;
import org.example.techtalksskillbasedrecruitment.modules.application.dto.response.CandidateAppsResponse;
import org.example.techtalksskillbasedrecruitment.modules.application.dto.response.MyApplicationsResponse;
import org.example.techtalksskillbasedrecruitment.modules.candidateprofile.CandidateProfile;
import org.example.techtalksskillbasedrecruitment.modules.candidateprofile.CandidateProfileRepository;
import org.example.techtalksskillbasedrecruitment.common.exceptions.ResourceNotFoundException;
import org.example.techtalksskillbasedrecruitment.modules.job.Job;
import org.example.techtalksskillbasedrecruitment.modules.job.JobRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final CandidateProfileRepository candidateProfileRepository;

    public ApplicationService(ApplicationRepository applicationRepository, JobRepository jobRepository, CandidateProfileRepository candidateProfileRepository) {
        this.applicationRepository = applicationRepository;
        this.jobRepository = jobRepository;
        this.candidateProfileRepository = candidateProfileRepository;
    }

    public ApplicationResponse applyJobController(ApplicationRequest applicationRequest) {

        Job job = this.jobRepository.findById(applicationRequest.getJobId()).orElseThrow(()-> new ResourceNotFoundException("Invalid Job id. Job does not exist to apply to it."));
        CandidateProfile candidateProfile = this.candidateProfileRepository.findById(applicationRequest.getCandidateId()).orElseThrow(()->new ResourceNotFoundException(" Candidate profile not found. Cannot apply to job"));
        Application application = new Application();
        application.setJob(job);
        application.setCandidate(candidateProfile);
        Application newApp = this.applicationRepository.save(application);
        return new ApplicationResponse(newApp.getApplicationId(),newApp.getJob().getJobId(),newApp.getCandidate().getCandidateId(),newApp.getStatus());
    }


    public Map<String,Boolean> isAppliedToJobService(Integer jobId, Integer candidateId){
        Job job = this.jobRepository.findById(jobId).orElseThrow(()->new ResourceNotFoundException("Invalid job id. Failed in checking whether candidate applied to the job"));
        CandidateProfile candidateProfile = this.candidateProfileRepository.findById(candidateId).orElseThrow(()->new ResourceNotFoundException("Invalid candidate id. Failed in checking whether candidate applied to the job"));
        Application application = this.applicationRepository.findByJobAndCandidate(job,candidateProfile);
        boolean isApplied;
        if (application == null) {
            isApplied = false;
        }
        else {
            isApplied = true;
        }
        Map<String,Boolean> response = new HashMap<>();
        response.put("isApplied",isApplied);
        return response;
    }


    public List<MyApplicationsResponse> fetchMyAppsService(Integer candidateId){
        return this.applicationRepository.fetchMyApps(candidateId);
    }


    public List<CandidateAppsResponse> findCandidatesAppsService(Integer jobId){
        return this.applicationRepository.findCandidatesAppsRepo(jobId);
    }

    public Map<String,String> approveCandidateAppService(Integer appId){
        Application application = this.applicationRepository.findById(appId).orElseThrow(()->new ResourceNotFoundException("Invalid application id. Candidate App is not found to approve it"));
        application.setStatus("APPROVED");
        Application updatedApp = this.applicationRepository.save(application);
        Map<String,String> response = new HashMap<>();
        response.put("status",updatedApp.getStatus());
        return response;
    }


    public Map<String,String> rejectCandidateAppService(Integer appId){
        Application application = this.applicationRepository.findById(appId).orElseThrow(()->new ResourceNotFoundException("Invalid application id. Candidate App is not found to approve it"));
        application.setStatus("REJECTED");
        Application updatedApp = this.applicationRepository.save(application);
        Map<String,String> response = new HashMap<>();
        response.put("status",updatedApp.getStatus());
        return response;
    }


}
