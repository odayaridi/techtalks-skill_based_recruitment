package org.example.techtalksskillbasedrecruitment.modules.companyrecruiter;

import jakarta.persistence.*;
import org.example.techtalksskillbasedrecruitment.modules.company.Company;
import org.example.techtalksskillbasedrecruitment.modules.user.User;

@Entity
@Table(
        name = "company_recruiters",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_company_recruiter",
                        columnNames = {"company_id", "user_id"}
                )
        }
)
public class CompanyRecruiter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_recruiter_id")
    private Integer companyRecruiterId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public CompanyRecruiter() {}

    public Integer getCompanyRecruiterId() {
        return companyRecruiterId;
    }

    public void setCompanyRecruiterId(Integer companyRecruiterId) {
        this.companyRecruiterId = companyRecruiterId;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}