package org.example.techtalksskillbasedrecruitment.companyrecruiter;

import org.example.techtalksskillbasedrecruitment.company.Company;
import org.example.techtalksskillbasedrecruitment.company.CompanyRepository;
import org.example.techtalksskillbasedrecruitment.companyrecruiter.dto.request.CreateCompanyRecruiterRequest;
import org.example.techtalksskillbasedrecruitment.companyrecruiter.dto.response.CompanyRecruiterDTO;
import org.example.techtalksskillbasedrecruitment.companyrecruiter.dto.response.CreateCompanyRecruiterResponse;
import org.example.techtalksskillbasedrecruitment.exceptions.ResourceNotFoundException;
import org.example.techtalksskillbasedrecruitment.user.User;
import org.example.techtalksskillbasedrecruitment.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyRecruiterService {
    private final CompanyRecruiterRepository companyRecruiterRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    public CompanyRecruiterService(CompanyRecruiterRepository companyRecruiterRepository, CompanyRepository companyRepository, UserRepository userRepository) {
        this.companyRecruiterRepository = companyRecruiterRepository;
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    public CreateCompanyRecruiterResponse createCompanyRecruiterService(CreateCompanyRecruiterRequest companyRecruiterReq) {
        Company company = this.companyRepository.findByCompanyName(companyRecruiterReq.getCompanyName());
        if(company == null) {
            throw new ResourceNotFoundException("Company does not exist to assign a recruiter to it");
        }
        User existingUser = this.userRepository.findById(companyRecruiterReq.getUserId()).orElseThrow(() ->
                new ResourceNotFoundException("User does not exist with this id to be assigned to a company"));
        CompanyRecruiter companyRecruiter = new CompanyRecruiter();
        companyRecruiter.setCompany(company);
        companyRecruiter.setUser(existingUser);

        CompanyRecruiter newCompanyRecruiter = this.companyRecruiterRepository.save(companyRecruiter);
        return new CreateCompanyRecruiterResponse(newCompanyRecruiter.getCompanyRecruiterId(),
                newCompanyRecruiter.getCompany().getCompanyId(),newCompanyRecruiter.getUser().getUserId());
    }


    public List<CompanyRecruiterDTO> getCompanyRecruitersService(){
        return this.companyRecruiterRepository.getCompanyRecruitersRepository();
    }
}
