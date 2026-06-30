package org.example.techtalksskillbasedrecruitment.jobskill;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/job-skill")
public class JobSkillController {
    private final JobSkillService jobSkillService;

    public JobSkillController(JobSkillService jobSkillService) {
        this.jobSkillService = jobSkillService;
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteJobSkill(@RequestParam Integer jobId,
                                               @RequestParam String skillName) {
        jobSkillService.deleteJobSkillService(jobId, skillName);
        return ResponseEntity.noContent().build();
    }
}
