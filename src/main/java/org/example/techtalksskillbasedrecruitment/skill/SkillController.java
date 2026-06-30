package org.example.techtalksskillbasedrecruitment.skill;


import jakarta.validation.Valid;
import org.example.techtalksskillbasedrecruitment.skill.dto.request.SkillRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
public class SkillController {
    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @PostMapping("/create")
    public ResponseEntity<Skill> createSkill(@Valid @RequestBody SkillRequest request) {
        return ResponseEntity.ok(skillService.createSkillService(request));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Skill>> getAllSkills() {
        return ResponseEntity.ok(skillService.getAllSkillsService());
    }

    @GetMapping("/get/skillId/{skillId}")
    public ResponseEntity<Skill> getSkillById(@PathVariable Integer skillId) {
        return ResponseEntity.ok(skillService.getSkillByIdService(skillId));
    }

    @PutMapping("/update/skillId/{skillId}")
    public ResponseEntity<Skill> updateSkill(@PathVariable Integer skillId, @Valid @RequestBody SkillRequest request) {
        return ResponseEntity.ok(skillService.updateSkillService(skillId, request));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteSkill(@RequestParam Integer skillId) {
        skillService.deleteSkillService(skillId);
        return ResponseEntity.noContent().build();
    }
}
