package org.example.techtalksskillbasedrecruitment.company;

import org.example.techtalksskillbasedrecruitment.company.dto.request.CompanyRequest;
import org.example.techtalksskillbasedrecruitment.exceptions.ConflictException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Company createCompanyService(CompanyRequest request) {
        Company existing = companyRepository.findByWebsite(request.getWebsite());
        if (existing != null) {
            throw new ConflictException("Same company already exists with this website link");
        }

        Company company = new Company();
        company.setCompanyName(request.getCompanyName());
        company.setDescription(request.getDescription());
        company.setLocation(request.getLocation());
        company.setWebsite(request.getWebsite());
        company.setLogoPath(request.getLogoPath());

        return companyRepository.save(company);
    }

    public List<Company> getAllCompaniesService() {
        return companyRepository.findAll();
    }
}
