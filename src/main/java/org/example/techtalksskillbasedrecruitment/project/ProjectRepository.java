package org.example.techtalksskillbasedrecruitment.project;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    boolean existsByGithubUrlAndProjectIdNot(String githubUrl, Integer projectId);
    boolean existsByDemoUrlAndProjectIdNot(String demoUrl, Integer projectId);
}
