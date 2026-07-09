package org.example.techtalksskillbasedrecruitment.company;


import org.example.techtalksskillbasedrecruitment.company.dto.request.CompanyRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowCredentials = "false")
@RestController
@RequestMapping("/api/company")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/create")
    public ResponseEntity<Company> createCompanyController(@RequestBody CompanyRequest companyRequest){
        Company newCompany = this.companyService.createCompanyService(companyRequest);
        return new ResponseEntity<>(newCompany, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Company> updateCompanyController(@RequestParam Integer companyId, @RequestBody CompanyRequest companyRequest) {
         Company updatedCompany = this.companyService.updateCompanyService(companyId,companyRequest);
         return new ResponseEntity<>(updatedCompany,HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Company>> getAllCompaniesController(){
        List<Company> companiesList = this.companyService.getAllCompaniesService();
        return ResponseEntity.ok(companiesList);
    }

    @DeleteMapping("/delete/companyId/{companyId}")
    public ResponseEntity<Void> deleteCompanyController(@PathVariable Integer companyId){
        this.companyService.deleteCompanyService(companyId);
        return ResponseEntity.noContent().build();
    }
}
