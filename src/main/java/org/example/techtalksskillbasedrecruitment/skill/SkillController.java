package org.example.techtalksskillbasedrecruitment.skill;


import org.example.techtalksskillbasedrecruitment.common.response.PaginatedResponse;
import org.example.techtalksskillbasedrecruitment.common.response.PaginationMeta;
import org.example.techtalksskillbasedrecruitment.skill.dto.request.SkillRequest;
import org.example.techtalksskillbasedrecruitment.skill.dto.response.SkillResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowCredentials = "false")
@RestController
@RequestMapping("/api/skill")
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @PostMapping("/create")
    public ResponseEntity<Skill> createSkillController(@RequestBody SkillRequest createSkillRequest){
        Skill newSkill= this.skillService.createSkillService(createSkillRequest);
        return new ResponseEntity<>(newSkill, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Skill> updateSkillController(@RequestParam Integer skillId,@RequestBody SkillRequest skillRequest){
        Skill updatedSkill = this.skillService.updateSkillService(skillId,skillRequest);
        return new ResponseEntity<>(updatedSkill,HttpStatus.OK);
    }


    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteSkillController(@RequestParam Integer skillId) {
        this.skillService.deleteSkillService(skillId);
        return ResponseEntity.noContent().build();
    }

//    @GetMapping("/getAll")
//    public ResponseEntity<List<Skill>> fetchALlSkillsController() {
//        List<Skill> skillsList = this.skillService.fetchAllSkillsService();
//        return new ResponseEntity<>(skillsList,HttpStatus.OK);
//    }



//    @GetMapping("/getAll")
//    public ResponseEntity<Page<Skill>> fetchAllSkillsController(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int limit
//    ) {
//        Pageable pageable = PageRequest.of(page, limit);
//
//        Page<Skill> skillsPage = this.skillService.fetchAllSkillsService(pageable);
//
//        return new ResponseEntity<>(skillsPage, HttpStatus.OK);
//    }

//
    @GetMapping("/getAll")
    public ResponseEntity<PaginatedResponse<SkillResponse>> getAllSkills(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ) {

        Pageable pageable = PageRequest.of(page, limit);

        Page<Skill> skillPage = skillService.fetchAllSkillsService(pageable);




        List<SkillResponse> data = new ArrayList<>();
        for (Skill skill : skillPage.getContent()) {
            SkillResponse response = new SkillResponse(
                    skill.getSkillId(),
                    skill.getSkillName()
            );
            data.add(response);
        }

        PaginationMeta meta = new PaginationMeta(
                skillPage.getNumber(),
                skillPage.getSize(),
                skillPage.getTotalElements(),
                skillPage.getTotalPages(),
                skillPage.isFirst(),
                skillPage.isLast()
        );

        PaginatedResponse<SkillResponse> response = new PaginatedResponse<>(data, meta);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
