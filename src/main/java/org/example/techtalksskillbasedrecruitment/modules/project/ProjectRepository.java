package org.example.techtalksskillbasedrecruitment.modules.project;

import org.example.techtalksskillbasedrecruitment.modules.candidateprofile.CandidateProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Integer> {
    Project findByGithubUrlOrDemoUrlAndProjectIdNot(String githubUrl, String demoUrl, Integer projectId);
    Project findByGithubUrlOrDemoUrl(String githubUrl, String demoUrl);
    List<Project> findByCandidate(CandidateProfile candidateProfile);
}
