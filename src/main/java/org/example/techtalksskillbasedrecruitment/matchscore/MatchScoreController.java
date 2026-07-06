package org.example.techtalksskillbasedrecruitment.matchscore;

import org.example.techtalksskillbasedrecruitment.ai.dto.response.DetectedSkill;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@CrossOrigin(origins = "*", allowCredentials = "false")
@RestController
@RequestMapping("/api/match-scores")
public class MatchScoreController {

    private final MatchScoreService matchScoreService;

    public MatchScoreController(MatchScoreService matchScoreService) {
        this.matchScoreService = matchScoreService;
    }

    @PostMapping("/analyze/job/{jobId}")
    public ResponseEntity<String> analyzeJobController(@PathVariable Integer jobId)  throws Exception{
        String response = this.matchScoreService.analyzeJobService(jobId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/test-groq/project/{projectId}")
    public ResponseEntity<List<DetectedSkill>> testGroqController(@PathVariable Integer projectId) throws Exception {
        List<DetectedSkill> detectedSkills = this.matchScoreService.testGroqService(projectId);

        return new ResponseEntity<>(detectedSkills, HttpStatus.OK);
    }

}