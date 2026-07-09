package org.example.techtalksskillbasedrecruitment.project;

import jakarta.transaction.Transactional;
import org.example.techtalksskillbasedrecruitment.GroqSkillAnalyzer.GroqSkillAnalyzerService;
import org.example.techtalksskillbasedrecruitment.GroqSkillAnalyzer.dto.ExtractedSkillDTO;
import org.example.techtalksskillbasedrecruitment.candidateprofile.CandidateProfile;
import org.example.techtalksskillbasedrecruitment.candidateprofile.CandidateProfileRepository;
import org.example.techtalksskillbasedrecruitment.candidateskill.CandidateSkillRepository;
import org.example.techtalksskillbasedrecruitment.candidateskillscore.CandidateSkillScore;
import org.example.techtalksskillbasedrecruitment.candidateskillscore.CandidateSkillScoreRepository;
import org.example.techtalksskillbasedrecruitment.exceptions.ConflictException;
import org.example.techtalksskillbasedrecruitment.exceptions.ResourceNotFoundException;
import org.example.techtalksskillbasedrecruitment.project.dto.request.ProjectRequest;
import org.example.techtalksskillbasedrecruitment.project.dto.request.UpdateProjectRequest;
import org.example.techtalksskillbasedrecruitment.project.dto.response.ProjectResponse;
import org.example.techtalksskillbasedrecruitment.skill.Skill;
import org.example.techtalksskillbasedrecruitment.skill.SkillRepository;
import org.example.techtalksskillbasedrecruitment.skillevidence.SkillEvidence;
import org.example.techtalksskillbasedrecruitment.skillevidence.SkillEvidenceRepository;
import org.example.techtalksskillbasedrecruitment.skillevidence.dto.SkillAverageScoreDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final CandidateProfileRepository candidateProfileRepository;
    private final SkillEvidenceRepository skillEvidenceRepository;
    private final GroqSkillAnalyzerService groqSkillAnalyzerService;
    private final SkillRepository skillRepository;
    private final CandidateSkillScoreRepository candidateSkillScoreRepository;

    public ProjectService(ProjectRepository projectRepository, CandidateProfileRepository candidateProfileRepository, SkillEvidenceRepository skillEvidenceRepository, GroqSkillAnalyzerService groqSkillAnalyzerService, SkillRepository skillRepository, CandidateSkillScoreRepository candidateSkillScoreRepository) {
        this.projectRepository = projectRepository;
        this.candidateProfileRepository = candidateProfileRepository;
        this.skillEvidenceRepository = skillEvidenceRepository;
        this.groqSkillAnalyzerService = groqSkillAnalyzerService;
        this.skillRepository = skillRepository;
        this.candidateSkillScoreRepository = candidateSkillScoreRepository;
    }

    @Transactional
    public ProjectResponse createProjectService(ProjectRequest projectRequest){
        CandidateProfile candidateProfile  = this.candidateProfileRepository.findById(projectRequest.getCandidateId()).orElseThrow(() -> new ResourceNotFoundException("Invalid Candidate profile id. Cannot upload a project"));
        Project exisitngProject = this.projectRepository.findByGithubUrlOrDemoUrl(projectRequest.getGithubUrl(),projectRequest.getDemoUrl());
        if(exisitngProject != null) {
            throw new ConflictException("Project Github or demo already exists. Upload a new one.");
        }
        Project project = new Project();
        project.setProjectName(projectRequest.getProjectName());
        project.setCandidate(candidateProfile);
        project.setDemoUrl(projectRequest.getDemoUrl());
        project.setDescription(projectRequest.getDescription());
        project.setStartDate(projectRequest.getStartDate());
        project.setEndDate(projectRequest.getEndDate());
        project.setGithubUrl(projectRequest.getGithubUrl());
        Project newProject = this.projectRepository.save(project);
        List<Skill> skillsList = this.skillRepository.findAll();
      List<ExtractedSkillDTO> skillDTOS =  this.groqSkillAnalyzerService.analyzeProjectSkills(newProject.getProjectName(),newProject.getDescription(), newProject.getGithubUrl(),skillsList); // Pass skillsList
        for (ExtractedSkillDTO es : skillDTOS) {
            SkillEvidence skillEvidence = new SkillEvidence();
            skillEvidence.setReferenceId(newProject.getProjectId());
            skillEvidence.setEvidenceType("PROJECT");
            Skill skill = this.skillRepository.findBySkillName(es.getSkillName());
            if(skill == null){
                throw new ResourceNotFoundException("Invalid score name. Not found");
            }
            skillEvidence.setSkill(skill);
            skillEvidence.setCandidate(candidateProfile);
            skillEvidence.setScore(es.getScore());
            this.skillEvidenceRepository.save(skillEvidence);
            }
        candidateSkillScoreRepository.deleteByCandidate(candidateProfile);
        candidateSkillScoreRepository.flush();
        List<SkillAverageScoreDto> skillAverageScoreDtos = this.skillEvidenceRepository.findAverageScorePerSkill(candidateProfile.getCandidateId());
        for (SkillAverageScoreDto sas : skillAverageScoreDtos) {

            System.out.println("SkillId = " + sas.getSkillId());
            CandidateSkillScore candidateSkillScore = new CandidateSkillScore();
            Skill skill = this.skillRepository.findById(sas.getSkillId()).orElseThrow(()->new ResourceNotFoundException("Skill not found"));
            candidateSkillScore.setSkill(skill);
            candidateSkillScore.setCandidate(candidateProfile);
            candidateSkillScore.setScore(sas.getAverageScore());
            this.candidateSkillScoreRepository.save(candidateSkillScore);
        }
        return new ProjectResponse(newProject.getProjectId(),newProject.getCandidate().getCandidateId(),
                newProject.getProjectName(), newProject.getDescription(),newProject.getStartDate(),
                newProject.getEndDate(),newProject.getGithubUrl(),newProject.getDemoUrl(),
                newProject.getCreatedAt() );
    }


    @Transactional
    public ProjectResponse updateProjectService(Integer projectId, Integer candidateId ,UpdateProjectRequest projectRequest){
        CandidateProfile candidateProfile = this.candidateProfileRepository.findById(candidateId).orElseThrow(()->new ResourceNotFoundException("Invalid candidateId. Cannot update project details"));
        Project project = this.projectRepository.findById(projectId).orElseThrow(()-> new ResourceNotFoundException("Invalid Project Id. Project does not exist to update it"));
        Project exisitngProject = this.projectRepository.findByGithubUrlOrDemoUrlAndProjectIdNot(projectRequest.getGithubUrl(),projectRequest.getDemoUrl(),projectId);
        if(exisitngProject != null) {
            throw new ConflictException("Project Github or demo already exists. Upload a new one.");
        }
        project.setGithubUrl(projectRequest.getGithubUrl());
        project.setDemoUrl(projectRequest.getDemoUrl());
        project.setProjectName(projectRequest.getProjectName());
        project.setDescription(projectRequest.getDescription());
        project.setStartDate(projectRequest.getStartDate());
        project.setEndDate(projectRequest.getEndDate());
        Project updatedProject = this.projectRepository.save(project);



        List<Skill> skillsList = this.skillRepository.findAll();
        List<ExtractedSkillDTO> skillDTOS =  this.groqSkillAnalyzerService.analyzeProjectSkills(updatedProject.getProjectName(),updatedProject.getDescription(), updatedProject.getGithubUrl(),skillsList);
        this.skillEvidenceRepository.deleteByCandidateAndEvidenceTypeAndReferenceId(
                candidateProfile,
                "PROJECT",
                updatedProject.getProjectId()
        );

        for (ExtractedSkillDTO es : skillDTOS) {
            SkillEvidence skillEvidence = new SkillEvidence();
            skillEvidence.setReferenceId(updatedProject.getProjectId());
            skillEvidence.setEvidenceType("PROJECT");
            Skill skill = this.skillRepository.findBySkillName(es.getSkillName());
            if(skill == null){
                throw new ResourceNotFoundException("Invalid score name. Not found");
            }
            skillEvidence.setSkill(skill);
            skillEvidence.setCandidate(candidateProfile);
            skillEvidence.setScore(es.getScore());
            this.skillEvidenceRepository.save(skillEvidence);
        }
        this.candidateSkillScoreRepository.deleteByCandidate(candidateProfile);
        candidateSkillScoreRepository.flush();
        List<SkillAverageScoreDto> skillAverageScoreDtos = this.skillEvidenceRepository.findAverageScorePerSkill(candidateProfile.getCandidateId());
        for (SkillAverageScoreDto sas : skillAverageScoreDtos) {
            CandidateSkillScore candidateSkillScore = new CandidateSkillScore();
            Skill skill = this.skillRepository.findById(sas.getSkillId()).orElseThrow(()->new ResourceNotFoundException("Skill not found"));
            candidateSkillScore.setSkill(skill);
            candidateSkillScore.setCandidate(candidateProfile);
            candidateSkillScore.setScore(sas.getAverageScore());
            this.candidateSkillScoreRepository.save(candidateSkillScore);
        }



        return new ProjectResponse(updatedProject.getProjectId(),updatedProject.getCandidate().getCandidateId(),
                updatedProject.getProjectName(), updatedProject.getDescription(),updatedProject.getStartDate(),
                updatedProject.getEndDate(),updatedProject.getGithubUrl(),updatedProject.getDemoUrl(),
                updatedProject.getCreatedAt() );
    }

    public List<ProjectResponse> getAllProjectsService(){
        List <Project> projects = this.projectRepository.findAll();
        List <ProjectResponse> projectResponseList= new ArrayList<>();
        for (Project p:projects){
            projectResponseList.add(
                    new ProjectResponse(p.getProjectId(),p.getCandidate().getCandidateId(),
                            p.getProjectName(), p.getDescription(),p.getStartDate(),
                            p.getEndDate(),p.getGithubUrl(),p.getDemoUrl(),
                            p.getCreatedAt() )
            );
        }
        return projectResponseList;
    }


    @Transactional
    public void deleteProjectService(Integer projectId, Integer candidateId) {

        CandidateProfile candidateProfile = this.candidateProfileRepository
                .findById(candidateId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Invalid candidate id. Cannot delete project."));

        Project project = this.projectRepository
                .findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Invalid project id. Failed to delete project."));

        // Delete all evidence generated by this project
        this.skillEvidenceRepository.deleteByCandidateAndEvidenceTypeAndReferenceId(
                candidateProfile,
                "PROJECT",
                project.getProjectId()
        );

        // Remove previous calculated scores
        this.candidateSkillScoreRepository.deleteByCandidate(candidateProfile);


        // Recalculate remaining scores
        List<SkillAverageScoreDto> averages =
                this.skillEvidenceRepository.findAverageScorePerSkill(
                        candidateProfile.getCandidateId()
                );

        for (SkillAverageScoreDto average : averages) {

            Skill skill = this.skillRepository.findById(average.getSkillId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Skill not found."));

            CandidateSkillScore candidateSkillScore = new CandidateSkillScore();
            candidateSkillScore.setCandidate(candidateProfile);
            candidateSkillScore.setSkill(skill);
            candidateSkillScore.setScore(average.getAverageScore());

            this.candidateSkillScoreRepository.save(candidateSkillScore);
        }

        // Finally delete the project
        this.projectRepository.delete(project);
    }
}
