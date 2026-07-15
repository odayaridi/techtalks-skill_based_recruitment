package org.example.techtalksskillbasedrecruitment.candidateprofile;

import org.example.techtalksskillbasedrecruitment.candidateprofile.dto.request.CreateCanProfileRequest;
import org.example.techtalksskillbasedrecruitment.candidateprofile.dto.request.UpdateProfileRequest;
import org.example.techtalksskillbasedrecruitment.candidateprofile.dto.response.CandidateProfileResponse;
import org.example.techtalksskillbasedrecruitment.security.annotation.CandidateOnly;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*", allowCredentials = "false")
@RestController
@RequestMapping("/api/candidate-profile")
public class CandidateProfileController {
    private final CandidateProfileService candidateProfileService;

    public CandidateProfileController(CandidateProfileService candidateProfileService) {
        this.candidateProfileService = candidateProfileService;
    }

    @PostMapping("/create")
    @CandidateOnly
    public ResponseEntity<CandidateProfileResponse> createCandidateProfileController(@RequestBody CreateCanProfileRequest profileReq) {
        CandidateProfileResponse newProfileResponse = this.candidateProfileService.createCandidateProfileService(profileReq);
        return new ResponseEntity<CandidateProfileResponse>(newProfileResponse, HttpStatus.CREATED);
    }

    @GetMapping("/exists/userId/{userId}")
    @CandidateOnly
    public ResponseEntity<Map<String,Boolean>> checkUserHasCandidateController(@PathVariable Integer userId) {
        Map<String,Boolean> response = this.candidateProfileService.checkUserHasCandidateService(userId);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/get/{userId}")
    @CandidateOnly
    public ResponseEntity<CandidateProfileResponse> getCandidateProfileController(@PathVariable Integer userId){
        CandidateProfileResponse candidateProfileResponse = this.candidateProfileService.getCandidateProfileService(userId);
        return new ResponseEntity<>(candidateProfileResponse,HttpStatus.OK);
    }
    @PutMapping("/update-profile")
    @CandidateOnly
    public  ResponseEntity<CandidateProfileResponse> updateCandidateProfileController(@RequestBody UpdateProfileRequest updateProfileRequest){
        CandidateProfileResponse candidateProfileResponse =
                this.candidateProfileService.updateCandidateProfileService(updateProfileRequest);

        return new ResponseEntity<>(candidateProfileResponse, HttpStatus.OK);
    }
}
