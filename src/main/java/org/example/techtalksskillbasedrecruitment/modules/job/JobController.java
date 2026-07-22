package org.example.techtalksskillbasedrecruitment.modules.job;

import org.example.techtalksskillbasedrecruitment.modules.job.dto.request.CreateJobRequest;
import org.example.techtalksskillbasedrecruitment.modules.job.dto.request.UpdateJobRequest;
import org.example.techtalksskillbasedrecruitment.modules.job.dto.response.JobResponse;
import org.example.techtalksskillbasedrecruitment.modules.matchscore.dto.response.MatchScoreResponse;
import org.example.techtalksskillbasedrecruitment.security.authorization.annotation.CandidateOnly;
import org.example.techtalksskillbasedrecruitment.security.authorization.annotation.RecruiterOnly;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/*
delete
 */
@CrossOrigin(origins = "*", allowCredentials = "false")
@RestController
@RequestMapping("/api/job")
public class JobController {
    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping("/postJob")
    @RecruiterOnly
    public ResponseEntity<JobResponse> postJobController(@RequestBody CreateJobRequest jobRequest) {
        JobResponse newJob = this.jobService.postJobService(jobRequest);
        return ResponseEntity.ok(newJob);
    }

    @GetMapping("/getByCompanyName")
    @CandidateOnly
    public ResponseEntity<List<JobResponse>> getJobsByCompanyNameController(
            @RequestParam String companyName) {

        List<JobResponse> jobs = this.jobService.getJobsByCompanyNameService(companyName);
        return ResponseEntity.ok(jobs);
    }


    @PutMapping("/updateJobDetails")
    @RecruiterOnly
    public ResponseEntity<JobResponse> updateJobDetailsController(
            @RequestParam Integer jobId,
           @RequestBody UpdateJobRequest jobRequest
    ) {
        JobResponse updatedJob = this.jobService.updateJobDetailsService(jobId,jobRequest);
        return new ResponseEntity<>(updatedJob, HttpStatus.OK);
    }

    @DeleteMapping("/delete/jobId/{jobId}")
    @RecruiterOnly
    public ResponseEntity<Void> deleteJobController(@PathVariable Integer jobId){
        this.jobService.deleteJobService(jobId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/fetchRecruiterJobs")
    @RecruiterOnly
    public ResponseEntity<List<JobResponse>> getPostedJobsByRecruiterController(
            @RequestParam Integer recruiterId) {

        List<JobResponse> jobs = this.jobService.getPostedJobsByRecruiterService(recruiterId);
        return ResponseEntity.ok(jobs);
    }


    @PostMapping("/calculateMatchScore")
    @RecruiterOnly
    public ResponseEntity<List<MatchScoreResponse>> calculateMatchScoresController(@RequestParam Integer jobId){
        List<MatchScoreResponse> matchScoreResponseList = this.jobService.calculateMatchScoresService(jobId);
        return new ResponseEntity<>(matchScoreResponseList,HttpStatus.CREATED);
    }
}
