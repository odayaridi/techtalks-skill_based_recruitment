package org.example.techtalksskillbasedrecruitment.modules.projectskill;


import org.example.techtalksskillbasedrecruitment.modules.projectskill.dto.request.CreateProjectSkill;
import org.example.techtalksskillbasedrecruitment.modules.projectskill.dto.response.ProjectSkillResponse;
import org.example.techtalksskillbasedrecruitment.security.authorization.annotation.CandidateOnly;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowCredentials = "false")
@RestController
@RequestMapping("/api/project-skill")
public class ProjectSkillController {
    private final ProjectSkillService projectSkillService;

    public ProjectSkillController(ProjectSkillService projectSkillService) {
        this.projectSkillService = projectSkillService;
    }

    @PostMapping("/create")
    @CandidateOnly
    public ResponseEntity<List<ProjectSkillResponse>> createProjectSkillsController(@RequestBody List<CreateProjectSkill> projectSkill) {
        List <ProjectSkillResponse> projectSkillResponsesList = this.projectSkillService.createProjectSkillService(projectSkill);
        return ResponseEntity.ok(projectSkillResponsesList);
    }

    @DeleteMapping("/delete")
    @CandidateOnly
    public ResponseEntity<Void> deleteProjectSkillController(@RequestParam Integer projectSkillId){
        this.projectSkillService.deleteProjectSkillService(projectSkillId);
        return ResponseEntity.noContent().build();
    }

}
