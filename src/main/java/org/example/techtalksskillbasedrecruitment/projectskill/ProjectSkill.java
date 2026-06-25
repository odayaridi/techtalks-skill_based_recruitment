package org.example.techtalksskillbasedrecruitment.projectskill;

import jakarta.persistence.*;
import org.example.techtalksskillbasedrecruitment.project.Project;
import org.example.techtalksskillbasedrecruitment.skill.Skill;

@Entity
@Table(
        name = "project_skills",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_project_skill",
                        columnNames = {"project_id", "skill_id"}
                )
        }
)
public class ProjectSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_skill_id")
    private Integer projectSkillId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne(optional = false)
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    public ProjectSkill() {}

    public Integer getProjectSkillId() {
        return projectSkillId;
    }

    public void setProjectSkillId(Integer projectSkillId) {
        this.projectSkillId = projectSkillId;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }
}