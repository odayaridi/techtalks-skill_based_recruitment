package org.example.techtalksskillbasedrecruitment.company;


import org.example.techtalksskillbasedrecruitment.company.dto.request.CompanyRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/create")
    public ResponseEntity<Company> createCompany(@RequestBody CompanyRequest request) {
        Company createdCompany = companyService.createCompanyService(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCompany);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Company>> getAllCompanies() {
        List<Company> companies = companyService.getAllCompaniesService();
        return ResponseEntity.status(HttpStatus.OK).body(companies);
    }
}
