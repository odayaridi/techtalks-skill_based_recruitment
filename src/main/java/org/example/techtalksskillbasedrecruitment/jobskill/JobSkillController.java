package org.example.techtalksskillbasedrecruitment.jobskill;
import org.example.techtalksskillbasedrecruitment.jobskill.dto.request.CreateJobSkill;
import org.example.techtalksskillbasedrecruitment.jobskill.dto.response.JobSkillResponse;
import org.example.techtalksskillbasedrecruitment.security.annotation.RecruiterOnly;
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
    @RecruiterOnly
    @PostMapping("/create")
    public ResponseEntity<List<JobSkillResponse>> createJobSkillsController(
            @RequestBody List<CreateJobSkill> jobSkillsList) {

        List<JobSkillResponse> jobSkills =
                this.jobSkillService.createJobSkillsService(jobSkillsList);

        return new ResponseEntity<>(jobSkills, HttpStatus.CREATED);
    }
    @RecruiterOnly
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteJobSkillController(@RequestParam Integer jobId, @RequestParam String skillName) {
        this.jobSkillService.deleteJobSkillService(jobId,skillName);
        return ResponseEntity.noContent().build();
    }
}
