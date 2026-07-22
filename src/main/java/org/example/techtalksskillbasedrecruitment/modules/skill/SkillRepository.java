package org.example.techtalksskillbasedrecruitment.modules.skill;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill,Integer> {
    Skill findBySkillName(String name);
    Skill findBySkillNameAndSkillIdNot(String skillName,Integer skillId);
}
