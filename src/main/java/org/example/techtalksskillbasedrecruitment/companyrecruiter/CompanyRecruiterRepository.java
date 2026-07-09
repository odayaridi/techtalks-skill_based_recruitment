package org.example.techtalksskillbasedrecruitment.companyrecruiter;


import org.example.techtalksskillbasedrecruitment.company.Company;
import org.example.techtalksskillbasedrecruitment.companyrecruiter.dto.response.CompanyRecruiterDTO;
import org.example.techtalksskillbasedrecruitment.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompanyRecruiterRepository extends JpaRepository<CompanyRecruiter, Integer> {

    @Query("""
        SELECT new org.example.techtalksskillbasedrecruitment.companyrecruiter.dto.response.CompanyRecruiterDTO(
            cr.companyRecruiterId,
            rv.username,
            c.companyName
        )
        FROM CompanyRecruiter cr
        JOIN RecruiterView rv ON rv.userId = cr.user.userId
        JOIN Company c ON c.companyId = cr.company.companyId
        """)
    List<CompanyRecruiterDTO> getCompanyRecruitersRepository();


}
