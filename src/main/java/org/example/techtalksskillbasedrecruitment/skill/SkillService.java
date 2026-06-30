package org.example.techtalksskillbasedrecruitment.skill;


import jakarta.transaction.Transactional;
import org.example.techtalksskillbasedrecruitment.exceptions.ResourceNotFoundException;
import org.example.techtalksskillbasedrecruitment.skill.dto.request.SkillRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService {
    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public Skill createSkillService(SkillRequest request) {
        Skill skill = new Skill();
        skill.setSkillName(request.getSkillName());
        return skillRepository.save(skill);
    }

    public List<Skill> getAllSkillsService() {
        return skillRepository.findAll();
    }

    public Skill getSkillByIdService(Integer skillId) {
        return skillRepository.findById(skillId).orElseThrow(() -> new ResourceNotFoundException("Skill not found with id: " + skillId));
    }

    public Skill updateSkillService(Integer skillId, SkillRequest request) {
        Skill skill = skillRepository.findById(skillId).orElseThrow(() -> new ResourceNotFoundException("Skill not found with id: " + skillId));
        skill.setSkillName(request.getSkillName());
        return skillRepository.save(skill);
    }
    @Transactional
    public void deleteSkillService(Integer skillId) {
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Skill not found with id: " + skillId));

        skillRepository.delete(skill);
    }
}
