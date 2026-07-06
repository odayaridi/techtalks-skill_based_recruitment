package org.example.techtalksskillbasedrecruitment.project;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Integer> {


    List<Project> findByCandidate_CandidateId(Integer candidateId);
}
