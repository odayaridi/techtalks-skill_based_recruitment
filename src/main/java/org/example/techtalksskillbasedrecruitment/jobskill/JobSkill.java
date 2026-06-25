package org.example.techtalksskillbasedrecruitment.jobskill;

import jakarta.persistence.*;
import org.example.techtalksskillbasedrecruitment.job.Job;
import org.example.techtalksskillbasedrecruitment.skill.Skill;

@Entity
@Table(
        name = "job_skills",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_job_skill",
                        columnNames = {"job_id", "skill_id"}
                )
        }
)
public class JobSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_skill_id")
    private Integer jobSkillId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @ManyToOne(optional = false)
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    @Column(name = "weight", nullable = false)
    private Integer weight;

    public JobSkill() {}

    public Integer getJobSkillId() {
        return jobSkillId;
    }

    public void setJobSkillId(Integer jobSkillId) {
        this.jobSkillId = jobSkillId;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}