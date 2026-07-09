package org.example.techtalksskillbasedrecruitment.matchscore;

import org.example.techtalksskillbasedrecruitment.candidateprofile.CandidateProfile;
import org.example.techtalksskillbasedrecruitment.job.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchScoreRepository extends JpaRepository<MatchScore,Integer> {

}
