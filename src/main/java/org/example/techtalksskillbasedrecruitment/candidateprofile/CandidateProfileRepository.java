package org.example.techtalksskillbasedrecruitment.candidateprofile;

import org.example.techtalksskillbasedrecruitment.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateProfileRepository extends JpaRepository<CandidateProfile,Integer> {
    boolean existsByUser(User user);
    CandidateProfile findByUser(User user);

}


