package org.example.techtalksskillbasedrecruitment.modules.project;


import org.example.techtalksskillbasedrecruitment.modules.candidateprofile.CandidateProfile;
import org.example.techtalksskillbasedrecruitment.modules.project.dto.request.ProjectRequest;
import org.example.techtalksskillbasedrecruitment.modules.project.dto.request.UpdateProjectRequest;
import org.example.techtalksskillbasedrecruitment.modules.project.dto.response.ProjectResponse;
import org.example.techtalksskillbasedrecruitment.security.authorization.annotation.CandidateOnly;
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
    @CandidateOnly
    public ResponseEntity<ProjectResponse> createProjectController(@RequestBody ProjectRequest projectRequest) {
        ProjectResponse newProject = this.projectService.createProjectService(projectRequest);
        return new ResponseEntity<ProjectResponse>(newProject, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    @CandidateOnly
    public ResponseEntity<ProjectResponse> updateProjectController(@RequestParam Integer projectId, @RequestBody UpdateProjectRequest projectRequest,  @RequestParam Integer candidateId){
        ProjectResponse projectResponse = this.projectService.updateProjectService(projectId,candidateId,projectRequest);
        return new ResponseEntity<>(projectResponse, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    @CandidateOnly
    public ResponseEntity<List<ProjectResponse>> getAllProjectsController( @RequestParam Integer candidateId) {
        List<ProjectResponse> projectResponseList = this.projectService.getAllProjectsService(candidateId);
        return ResponseEntity.ok(projectResponseList);
    }

    @DeleteMapping("/delete/projectId/{projectId}")
    @CandidateOnly
    public ResponseEntity<Void> deleteProjectController(@PathVariable Integer projectId, @RequestParam Integer candidateId){
        this.projectService.deleteProjectService(projectId,candidateId);
        return ResponseEntity.noContent().build();
    }
}
