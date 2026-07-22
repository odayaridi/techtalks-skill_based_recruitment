package org.example.techtalksskillbasedrecruitment.modules.application;

import org.example.techtalksskillbasedrecruitment.modules.application.dto.request.ApplicationRequest;
import org.example.techtalksskillbasedrecruitment.modules.application.dto.response.ApplicationResponse;
import org.example.techtalksskillbasedrecruitment.modules.application.dto.response.CandidateAppsResponse;
import org.example.techtalksskillbasedrecruitment.modules.application.dto.response.MyApplicationsResponse;
import org.example.techtalksskillbasedrecruitment.security.authorization.annotation.CandidateOnly;
import org.example.techtalksskillbasedrecruitment.security.authorization.annotation.RecruiterOnly;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowCredentials = "false")
@RestController
@RequestMapping("/api/application")
public class ApplicationController {
    private final ApplicationService applicationService;


    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping("/create")
    @CandidateOnly
    public ResponseEntity<ApplicationResponse> applyJobController(@RequestBody ApplicationRequest applicationRequest){
        ApplicationResponse applicationResponse = this.applicationService.applyJobController(applicationRequest);
        return ResponseEntity.ok(applicationResponse);
    }

    @GetMapping("/isApplied")
    @CandidateOnly
    public ResponseEntity<Map<String,Boolean>> isAppliedToJobController(@RequestParam Integer jobId, @RequestParam Integer candidateId){
        Map<String,Boolean> response = this.applicationService.isAppliedToJobService(jobId,candidateId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/fetchMyApps")
    @CandidateOnly
    public ResponseEntity<List<MyApplicationsResponse>> fetchMyAppsController(@RequestParam Integer candidateId){
        List<MyApplicationsResponse> myApplicationsResponseList = this.applicationService.fetchMyAppsService(candidateId);
        return ResponseEntity.ok(myApplicationsResponseList);
    }

    @GetMapping("/findJobCandidateApps")
    @RecruiterOnly
    public ResponseEntity<List<CandidateAppsResponse>> findCandidatesAppsController(@RequestParam Integer jobId){
        List<CandidateAppsResponse> candidateAppsResponseList = this.applicationService.findCandidatesAppsService(jobId);
        return ResponseEntity.ok(candidateAppsResponseList);
    }

    @PutMapping("/approveCandidateApp")
    @RecruiterOnly
    public ResponseEntity<Map<String,String>> approveCandidateAppController(@RequestParam Integer applicationId){
        Map<String,String> response = this.applicationService.approveCandidateAppService(applicationId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/rejectCandidateApp")
    @RecruiterOnly
    public ResponseEntity<Map<String,String>> rejectCandidateAppController(@RequestParam Integer applicationId){
        Map<String,String> response = this.applicationService.rejectCandidateAppService(applicationId);
        return ResponseEntity.ok(response);
    }
}
