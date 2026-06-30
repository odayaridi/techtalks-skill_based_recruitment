package org.example.techtalksskillbasedrecruitment.projectskill;

import org.example.techtalksskillbasedrecruitment.projectskill.dto.request.Createprojectskill;
import org.example.techtalksskillbasedrecruitment.projectskill.dtoresponse.ProjectskillResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowCredentials = "false")
@RestController
@RequestMapping("/api/project-skill")
public class ProjectSkillController {
  private  final   ProjectSkillService projectSkillService;
    public  ProjectSkillController(ProjectSkillService projectSkillService){
        this.projectSkillService=projectSkillService;
    }

    @PostMapping("/create")
    public ResponseEntity<List<ProjectskillResponse>> createProjectSkillController(
            @RequestBody List<Createprojectskill> createProjectSkills) {
        List<ProjectskillResponse> responses=this.projectSkillService.createProjectSkillService(createProjectSkills);
        return new ResponseEntity<>(responses,HttpStatus.CREATED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteProjectSkillController(@RequestParam Integer projectSkillId) {
        this.projectSkillService.deleteProjectSkillService(projectSkillId);

        return ResponseEntity.noContent().build();
    }

}
