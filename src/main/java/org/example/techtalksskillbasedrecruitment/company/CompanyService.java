package org.example.techtalksskillbasedrecruitment.company;


import org.example.techtalksskillbasedrecruitment.company.dto.request.CompanyRequest;
import org.example.techtalksskillbasedrecruitment.exceptions.ConflictException;
import org.example.techtalksskillbasedrecruitment.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;


    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }


    public Company createCompanyService(CompanyRequest companyRequest){
        Company companyExists = this.companyRepository.findByWebsite(companyRequest.getWebsite());
        if (companyExists != null) {
            throw new ConflictException("Same company already exists with this website link");
        }
        Company company = new Company();
        company.setCompanyName(companyRequest.getCompanyName());
        company.setDescription(companyRequest.getDescription());
        company.setLocation(companyRequest.getLocation());
        company.setWebsite(companyRequest.getWebsite());
        company.setLogoPath(companyRequest.getLogoPath());
        return this.companyRepository.save(company);
    }

    public Company updateCompanyService(Integer companyId,CompanyRequest companyRequest) {
        Company company = this.companyRepository.findById(companyId).orElseThrow(()->new ResourceNotFoundException("Invalid Company Id. Company is not found to update it"));
        Company companyConflict = this.companyRepository.findByWebsiteAndCompanyIdNot(companyRequest.getWebsite(),companyId);
        if (companyConflict!=null){
            throw new ConflictException("The entered website link already exists for another company.");
        }
        company.setCompanyName(companyRequest.getCompanyName());
        company.setDescription(companyRequest.getDescription());
        company.setLocation(companyRequest.getLocation());
        company.setWebsite(companyRequest.getWebsite());
        company.setLogoPath(companyRequest.getLogoPath());

        return  this.companyRepository.save(company);
    }

    public List<Company> getAllCompaniesService(){
        return this.companyRepository.findAll();
    }

    public void deleteCompanyService(Integer companyId){
        Company company = this.companyRepository.findById(companyId).orElseThrow(()->new ResourceNotFoundException("Invalid company id. Company not found to delete it"));
        this.companyRepository.delete(company);
    }
}

