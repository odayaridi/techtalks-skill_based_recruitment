package org.example.techtalksskillbasedrecruitment.project;

import org.example.techtalksskillbasedrecruitment.candidateprofile.CandidateProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Integer> {
    Project findByGithubUrlOrDemoUrlAndProjectIdNot(String githubUrl, String demoUrl, Integer projectId);
    Project findByGithubUrlOrDemoUrl(String githubUrl, String demoUrl);

}
