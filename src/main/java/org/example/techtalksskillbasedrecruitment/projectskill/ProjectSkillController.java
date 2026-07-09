package org.example.techtalksskillbasedrecruitment.projectskill;


import org.example.techtalksskillbasedrecruitment.projectskill.dto.request.CreateProjectSkill;
import org.example.techtalksskillbasedrecruitment.projectskill.dto.response.ProjectSkillResponse;
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
    public ResponseEntity<List<ProjectSkillResponse>> createProjectSkillsController(@RequestBody List<CreateProjectSkill> projectSkill) {
        List <ProjectSkillResponse> projectSkillResponsesList = this.projectSkillService.createProjectSkillService(projectSkill);
        return ResponseEntity.ok(projectSkillResponsesList);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteProjectSkillController(@RequestParam Integer projectSkillId){
        this.projectSkillService.deleteProjectSkillService(projectSkillId);
        return ResponseEntity.noContent().build();
    }

}
