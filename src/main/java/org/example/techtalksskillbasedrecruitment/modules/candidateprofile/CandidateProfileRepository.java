package org.example.techtalksskillbasedrecruitment.modules.candidateprofile;

import org.example.techtalksskillbasedrecruitment.modules.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateProfileRepository extends JpaRepository<CandidateProfile,Integer> {
    boolean existsByUser(User user);
    CandidateProfile findByUser(User user);

}


