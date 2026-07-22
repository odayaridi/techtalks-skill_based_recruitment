package org.example.techtalksskillbasedrecruitment.modules.candidateskillscore;

import jakarta.persistence.*;
import org.example.techtalksskillbasedrecruitment.modules.candidateprofile.CandidateProfile;
import org.example.techtalksskillbasedrecruitment.modules.skill.Skill;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "candidate_skill_scores",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_candidate_skill_score",
                        columnNames = {"candidate_id", "skill_id"}
                )
        }
)
public class CandidateSkillScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "candidate_id", nullable = false)
    private CandidateProfile candidate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    @Column(name = "score", precision = 5, scale = 2)
    private BigDecimal score;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();;

    public CandidateSkillScore() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public CandidateProfile getCandidate() { return candidate; }
    public void setCandidate(CandidateProfile candidate) { this.candidate = candidate; }

    public Skill getSkill() { return skill; }
    public void setSkill(Skill skill) { this.skill = skill; }

    public BigDecimal getScore() { return score; }
    public void setScore(BigDecimal score) { this.score = score; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
}