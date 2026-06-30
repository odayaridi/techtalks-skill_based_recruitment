package org.example.techtalksskillbasedrecruitment.job;

import org.example.techtalksskillbasedrecruitment.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job,Integer> {
    List<Job> findByCompany(Company company);

}
