package org.example.techtalksskillbasedrecruitment.matchscore;


import org.springframework.stereotype.Service;

@Service
public class MatchScoreService {
    private final MatchScoreRepository matchScoreRepository;

    public MatchScoreService(MatchScoreRepository matchScoreRepository) {
        this.matchScoreRepository = matchScoreRepository;
    }


}
