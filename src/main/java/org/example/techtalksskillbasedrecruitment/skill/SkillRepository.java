package org.example.techtalksskillbasedrecruitment.skill;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Integer> {
    Skill findBySkillName(String skillName);
}
