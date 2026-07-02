package org.example.techtalksskillbasedrecruitment.project;

import org.example.techtalksskillbasedrecruitment.candidateresume.dto.request.UpdateProjectRequest;
import org.example.techtalksskillbasedrecruitment.project.dto.request.ProjectRequest;
import org.example.techtalksskillbasedrecruitment.project.dto.response.ProjectResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/create")
    public ResponseEntity<ProjectResponse> createProjectController(@RequestBody ProjectRequest projectRequest) {
        ProjectResponse projectResponse = this.projectService.createProjectService(projectRequest);
        return new ResponseEntity<>(projectResponse, HttpStatus.CREATED);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<ProjectResponse>> getAllProjectsController() {
        List<ProjectResponse> projectResponses = this.projectService.getAllProjectsService();
        return new ResponseEntity<>(projectResponses, HttpStatus.OK);
    }
    @PutMapping("/update")
    public ResponseEntity<ProjectResponse> updateProjectController(
            @RequestParam Integer projectId,
            @RequestBody UpdateProjectRequest updateProjectRequest) {
        ProjectResponse projectResponse = this.projectService.updateProjectService(projectId, updateProjectRequest);
        return new ResponseEntity<>(projectResponse, HttpStatus.OK);
    }
}