package org.example.techtalksskillbasedrecruitment.company;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company,Integer> {
    Company findByCompanyName(String companyName);
}
