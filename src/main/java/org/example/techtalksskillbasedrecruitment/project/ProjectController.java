package org.example.techtalksskillbasedrecruitment.project;


import org.example.techtalksskillbasedrecruitment.project.dto.request.ProjectRequest;
import org.example.techtalksskillbasedrecruitment.project.dto.request.UpdateProjectRequest;
import org.example.techtalksskillbasedrecruitment.project.dto.response.ProjectResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowCredentials = "false")
@RestController
@RequestMapping("/api/project")
public class ProjectController {
    private final ProjectService projectService;


    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }


    @PostMapping("/create")
    public ResponseEntity<ProjectResponse> createProjectController(@RequestBody ProjectRequest projectRequest) {
        ProjectResponse newProject = this.projectService.createProjectService(projectRequest);
        return new ResponseEntity<ProjectResponse>(newProject, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<ProjectResponse> updateProjectController(@RequestParam Integer projectId, @RequestBody UpdateProjectRequest projectRequest,  @RequestParam Integer candidateId){
        ProjectResponse projectResponse = this.projectService.updateProjectService(projectId,candidateId,projectRequest);
        return new ResponseEntity<>(projectResponse, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ProjectResponse>> getAllProjectsController() {
        List<ProjectResponse> projectResponseList = this.projectService.getAllProjectsService();
        return ResponseEntity.ok(projectResponseList);
    }

    @DeleteMapping("/delete/projectId/{projectId}")
    public ResponseEntity<Void> deleteProjectController(@PathVariable Integer projectId, @RequestParam Integer candidateId){
        this.projectService.deleteProjectService(projectId,candidateId);
        return ResponseEntity.noContent().build();
    }
}
