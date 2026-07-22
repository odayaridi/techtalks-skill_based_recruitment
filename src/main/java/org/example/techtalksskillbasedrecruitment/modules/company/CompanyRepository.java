package org.example.techtalksskillbasedrecruitment.modules.company;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company,Integer> {
    Company findByCompanyName(String companyName);
    Company findByWebsite(String website);
    Company findByWebsiteAndCompanyIdNot(String website,Integer companyId);
}
