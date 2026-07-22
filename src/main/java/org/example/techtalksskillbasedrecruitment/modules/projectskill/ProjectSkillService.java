package org.example.techtalksskillbasedrecruitment.modules.projectskill;

import org.example.techtalksskillbasedrecruitment.common.exceptions.ResourceNotFoundException;
import org.example.techtalksskillbasedrecruitment.modules.project.Project;
import org.example.techtalksskillbasedrecruitment.modules.project.ProjectRepository;
import org.example.techtalksskillbasedrecruitment.modules.projectskill.dto.request.CreateProjectSkill;
import org.example.techtalksskillbasedrecruitment.modules.projectskill.dto.response.ProjectSkillResponse;
import org.example.techtalksskillbasedrecruitment.modules.skill.Skill;
import org.example.techtalksskillbasedrecruitment.modules.skill.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectSkillService {
    private final ProjectSkillRepository projectSkillRepository;
    private final ProjectRepository projectRepository;
    private final SkillRepository skillRepository;
    public ProjectSkillService(ProjectSkillRepository projectSkillRepository, ProjectRepository projectRepository, SkillRepository skillRepository) {
        this.projectSkillRepository = projectSkillRepository;
        this.projectRepository = projectRepository;
        this.skillRepository = skillRepository;
    }


    public List<ProjectSkillResponse> createProjectSkillService(List<CreateProjectSkill> projectSkills){
        List<ProjectSkillResponse> projectSkillResponses = new ArrayList<>();
        for (CreateProjectSkill ps : projectSkills) {
            ProjectSkill projectSkill = new ProjectSkill();
            Project project = this.projectRepository.findById(ps.getProjectId()).orElseThrow(()->new ResourceNotFoundException("Invalid Project Id. Cannot find the project to assign skill"));
            Skill skill = this.skillRepository.findBySkillName(ps.getSkillName());
            if(skill == null) {
                throw new ResourceNotFoundException("Invalid Skill Name. Cannot assign the skill to project");
            }
            projectSkill.setSkill(skill);
            projectSkill.setProject(project);
            ProjectSkill newProjectSkill = this.projectSkillRepository.save(projectSkill);
            projectSkillResponses.add(new ProjectSkillResponse(newProjectSkill.getProjectSkillId(),
                    newProjectSkill.getProject().getProjectId(),newProjectSkill.getSkill().getSkillName()));
        }
        return projectSkillResponses;
    }


    public void deleteProjectSkillService(Integer projectSkillId) {
        ProjectSkill toDeleteProjectSkill = this.projectSkillRepository.findById(projectSkillId).orElseThrow(()-> new ResourceNotFoundException("Invalid Project skill id. Cannot delete the project skill "));
        this.projectSkillRepository.delete(toDeleteProjectSkill);
    }

}
