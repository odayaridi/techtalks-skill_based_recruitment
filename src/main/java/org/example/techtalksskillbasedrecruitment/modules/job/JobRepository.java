package org.example.techtalksskillbasedrecruitment.modules.job;

import org.example.techtalksskillbasedrecruitment.modules.company.Company;
import org.example.techtalksskillbasedrecruitment.modules.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job,Integer> {
    List<Job> findByCompany(Company company);
    List<Job> findByCreatedBy(User createdBy);
}
