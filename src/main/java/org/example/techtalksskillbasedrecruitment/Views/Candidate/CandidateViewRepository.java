package org.example.techtalksskillbasedrecruitment.Views.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateViewRepository extends JpaRepository<CandidateView, Integer> {
}