package org.example.techtalksskillbasedrecruitment.matchscore;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MatchScoreRepository extends JpaRepository<MatchScore, Integer>{
    Optional<MatchScore> findByJob_JobIdAndCandidate_CandidateId(Integer jobid, Integer candidateid);
    List<MatchScore> findByJob_JobIdOrderByMatchPercentageDesc(Integer jobId);
}
