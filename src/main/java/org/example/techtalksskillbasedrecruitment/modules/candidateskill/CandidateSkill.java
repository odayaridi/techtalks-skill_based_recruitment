package org.example.techtalksskillbasedrecruitment.modules.candidateskill;

import jakarta.persistence.*;
import org.example.techtalksskillbasedrecruitment.modules.candidateprofile.CandidateProfile;
import org.example.techtalksskillbasedrecruitment.modules.skill.Skill;

@Entity
@Table(
        name = "candidate_skills",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_candidate_skill",
                        columnNames = {"candidate_id", "skill_id"}
                )
        }
)
public class CandidateSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "candidate_skill_id")
    private Integer candidateSkillId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "candidate_id", nullable = false)
    private CandidateProfile candidate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    @Column(name = "level", length = 50)
    private String level;

    @Column(name = "years_of_experience")
    private Integer yearsOfExperience;

    public CandidateSkill() {}

    public Integer getCandidateSkillId() {
        return candidateSkillId;
    }

    public void setCandidateSkillId(Integer candidateSkillId) {
        this.candidateSkillId = candidateSkillId;
    }

    public CandidateProfile getCandidate() {
        return candidate;
    }

    public void setCandidate(CandidateProfile candidate) {
        this.candidate = candidate;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(Integer yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }
}