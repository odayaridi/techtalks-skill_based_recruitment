package org.example.techtalksskillbasedrecruitment.application;

import org.example.techtalksskillbasedrecruitment.application.dto.request.ApplicationRequest;
import org.example.techtalksskillbasedrecruitment.application.dto.response.ApplicationResponse;
import org.example.techtalksskillbasedrecruitment.application.dto.response.CandidateAppsResponse;
import org.example.techtalksskillbasedrecruitment.application.dto.response.MyApplicationsResponse;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApplicationResponse> applyJobController(@RequestBody ApplicationRequest applicationRequest){
        ApplicationResponse applicationResponse = this.applicationService.applyJobController(applicationRequest);
        return ResponseEntity.ok(applicationResponse);
    }

    @GetMapping("/isApplied")
    public ResponseEntity<Map<String,Boolean>> isAppliedToJobController(@RequestParam Integer jobId, @RequestParam Integer candidateId){
        Map<String,Boolean> response = this.applicationService.isAppliedToJobService(jobId,candidateId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/fetchMyApps")
    public ResponseEntity<List<MyApplicationsResponse>> fetchMyAppsController(@RequestParam Integer candidateId){
        List<MyApplicationsResponse> myApplicationsResponseList = this.applicationService.fetchMyAppsService(candidateId);
        return ResponseEntity.ok(myApplicationsResponseList);
    }

    @GetMapping("/findJobCandidateApps")
    public ResponseEntity<List<CandidateAppsResponse>> findCandidatesAppsController(@RequestParam Integer jobId){
        List<CandidateAppsResponse> candidateAppsResponseList = this.applicationService.findCandidatesAppsService(jobId);
        return ResponseEntity.ok(candidateAppsResponseList);
    }

    @PutMapping("/approveCandidateApp")
    public ResponseEntity<Map<String,String>> approveCandidateAppController(@RequestParam Integer applicationId){
        Map<String,String> response = this.applicationService.approveCandidateAppService(applicationId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/rejectCandidateApp")
    public ResponseEntity<Map<String,String>> rejectCandidateAppController(@RequestParam Integer applicationId){
        Map<String,String> response = this.applicationService.rejectCandidateAppService(applicationId);
        return ResponseEntity.ok(response);
    }


}
