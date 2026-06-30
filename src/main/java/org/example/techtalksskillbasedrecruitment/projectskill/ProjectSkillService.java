package org.example.techtalksskillbasedrecruitment.projectskill;
import org.example.techtalksskillbasedrecruitment.project.Project;
import jakarta.transaction.Transactional;
import org.example.techtalksskillbasedrecruitment.exceptions.ResourceNotFoundException;
import org.example.techtalksskillbasedrecruitment.project.ProjectRepository;
import org.example.techtalksskillbasedrecruitment.skill.Skill;
import org.example.techtalksskillbasedrecruitment.skill.SkillRepository;
import org.springframework.stereotype.Service;
import org.example.techtalksskillbasedrecruitment.projectskill.dto.request.Createprojectskill;
import org.example.techtalksskillbasedrecruitment.projectskill.dtoresponse.ProjectskillResponse;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectSkillService {

    private final ProjectSkillRepository projectSkillRepository;
    private final ProjectRepository projectRepository;
    private final SkillRepository skillRepository;

    public ProjectSkillService(ProjectSkillRepository projectSkillRepository,
                               ProjectRepository projectRepository,
                               SkillRepository skillRepository) {
        this.projectSkillRepository = projectSkillRepository;
        this.projectRepository = projectRepository;
        this.skillRepository = skillRepository;
    }
    @Transactional
    public List<ProjectskillResponse> createProjectSkillService(List<Createprojectskill> createProjectSkills) {
        List<ProjectskillResponse> responses = new ArrayList<>();

        for (Createprojectskill request : createProjectSkills) {
            Project project = this.projectRepository.findById(request.getProjectID())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Project does not exist"));
            Skill skill=this.skillRepository.findBySkillName(request.getSkillname());
            if (skill == null) {
                throw new ResourceNotFoundException("Skill does not exist ");
            }
            ProjectSkill projectSkill=new ProjectSkill();
            projectSkill.setProject(project);
            projectSkill.setSkill(skill);
            ProjectSkill newproject=this.projectSkillRepository.save(projectSkill);
            responses.add(new ProjectskillResponse(newproject.getProjectSkillId(),newproject.getProject().getProjectId(),
                      newproject.getSkill().getSkillName()));
        }

        return responses;
    }
    @Transactional
    public void deleteProjectSkillService(Integer projectSkillId) {
        ProjectSkill projectSkill = this.projectSkillRepository.findById(projectSkillId)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Project Skill Id to delete"));

        this.projectSkillRepository.delete(projectSkill);
    }
}
