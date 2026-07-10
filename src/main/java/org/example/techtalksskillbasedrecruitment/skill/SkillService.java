package org.example.techtalksskillbasedrecruitment.skill;


import jakarta.transaction.Transactional;
import org.example.techtalksskillbasedrecruitment.exceptions.ConflictException;
import org.example.techtalksskillbasedrecruitment.exceptions.ResourceNotFoundException;
import org.example.techtalksskillbasedrecruitment.skill.dto.request.SkillRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService {

    private final SkillRepository skillRepository;


    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public Skill createSkillService(SkillRequest skillRequest) {
        Skill existingSkill = this.skillRepository.findBySkillName(skillRequest.getSkillName());
        if (existingSkill!=null) {
            throw new ConflictException("Skill already exists. Add a new one");
        }
        Skill skill = new Skill();
        skill.setSkillName(skillRequest.getSkillName());
        return this.skillRepository.save(skill);
    }

    public Skill updateSkillService(Integer skillId, SkillRequest skillRequest) {
        Skill existingSkill = this.skillRepository.findById(skillId).orElseThrow(() -> new ResourceNotFoundException("Skill id is not found to update"));
        Skill skillExists = this.skillRepository.findBySkillNameAndSkillIdNot(skillRequest.getSkillName(),skillId);
        if (skillExists!=null) {
            throw new ConflictException("The passed updated skill already exists. Update to a new skill name");
        }
        existingSkill.setSkillName(skillRequest.getSkillName());
        return this.skillRepository.save(existingSkill);
    }

    @Transactional
    public void deleteSkillService(Integer skillId){
        Skill existingSkill = this.skillRepository.findById(skillId).orElseThrow(() -> new ResourceNotFoundException("Skill does not exist to delete it"));
        this.skillRepository.delete(existingSkill);
    }

//    public List<Skill> fetchAllSkillsService(){
//        return this.skillRepository.findAll();
//    }

    public Page<Skill> fetchAllSkillsService(Pageable pageable)  {
        return this.skillRepository.findAll(pageable);
    }
}
