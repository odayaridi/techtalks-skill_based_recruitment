package org.example.techtalksskillbasedrecruitment.modules.jobskill;
import org.example.techtalksskillbasedrecruitment.modules.jobskill.dto.request.CreateJobSkill;
import org.example.techtalksskillbasedrecruitment.modules.jobskill.dto.response.JobSkillResponse;
import org.example.techtalksskillbasedrecruitment.security.authorization.annotation.RecruiterOnly;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/*delete */
@CrossOrigin(origins = "*", allowCredentials = "false")
@RestController
@RequestMapping("/api/job-skill")
public class JobSkillController {
    private final JobSkillService jobSkillService;

    public JobSkillController(JobSkillService jobSkillService) {
        this.jobSkillService = jobSkillService;
    }

    @PostMapping("/create")
    @RecruiterOnly
    public ResponseEntity<List<JobSkillResponse>> createJobSkillsController(
            @RequestBody List<CreateJobSkill> jobSkillsList) {

        List<JobSkillResponse> jobSkills =
                this.jobSkillService.createJobSkillsService(jobSkillsList);

        return new ResponseEntity<>(jobSkills, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    @RecruiterOnly
    public ResponseEntity<Void> deleteJobSkillController(@RequestParam Integer jobId, @RequestParam String skillName) {
        this.jobSkillService.deleteJobSkillService(jobId,skillName);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<JobSkillResponse>> getAllJobSkillsController(@RequestParam Integer jobId){
        List<JobSkillResponse> jobSkillResponseList = this.jobSkillService.getAllJobSkillsService(jobId);
        return ResponseEntity.ok(jobSkillResponseList);
    }
}
