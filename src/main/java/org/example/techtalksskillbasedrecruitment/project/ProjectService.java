package org.example.techtalksskillbasedrecruitment.project;

import org.example.techtalksskillbasedrecruitment.candidateprofile.CandidateProfile;
import org.example.techtalksskillbasedrecruitment.candidateprofile.CandidateProfileRepository;
import org.example.techtalksskillbasedrecruitment.exceptions.ResourceNotFoundException;
import org.example.techtalksskillbasedrecruitment.project.dto.request.ProjectRequest;
import org.example.techtalksskillbasedrecruitment.project.dto.response.ProjectResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final CandidateProfileRepository candidateProfileRepository;

    public ProjectService(ProjectRepository projectRepository, CandidateProfileRepository candidateProfileRepository) {
        this.projectRepository = projectRepository;
        this.candidateProfileRepository = candidateProfileRepository;
    }

    public ProjectResponse createProjectService(ProjectRequest projectRequest) {
        CandidateProfile candidateProfile = this.candidateProfileRepository.findById(projectRequest.getCandidateId())
                .orElseThrow(() -> new ResourceNotFoundException("Candidate with id " + projectRequest.getCandidateId() + " does not exist to create a project for him"));

        Project project = new Project();
        project.setCandidate(candidateProfile);
        project.setProjectName(projectRequest.getProjectName());
        project.setDescription(projectRequest.getDescription());
        project.setStartDate(projectRequest.getStartDate());
        project.setEndDate(projectRequest.getEndDate());
        project.setGithubUrl(projectRequest.getGithubUrl());
        project.setDemoUrl(projectRequest.getDemoUrl());

        Project savedProject = this.projectRepository.save(project);

        return new ProjectResponse(
                savedProject.getProjectId(),
                savedProject.getCandidate().getCandidateId(),
                savedProject.getProjectName(),
                savedProject.getDescription(),
                savedProject.getStartDate(),
                savedProject.getEndDate(),
                savedProject.getGithubUrl(),
                savedProject.getDemoUrl(),
                savedProject.getCreatedAt()
        );

    }
    public List<ProjectResponse> getAllProjectsService() {
        List<Project> projects = this.projectRepository.findAll();
        List<ProjectResponse> projectResponses = new ArrayList<>();

        for (Project project : projects) {
            ProjectResponse projectResponse = new ProjectResponse(
                    project.getProjectId(),
                    project.getCandidate().getCandidateId(),
                    project.getProjectName(),
                    project.getDescription(),
                    project.getStartDate(),
                    project.getEndDate(),
                    project.getGithubUrl(),
                    project.getDemoUrl(),
                    project.getCreatedAt()
            );
            projectResponses.add(projectResponse);
        }

        return projectResponses;
        }
    }
