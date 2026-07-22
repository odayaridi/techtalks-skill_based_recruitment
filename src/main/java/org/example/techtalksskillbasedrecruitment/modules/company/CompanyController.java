package org.example.techtalksskillbasedrecruitment.modules.company;


import org.example.techtalksskillbasedrecruitment.common.pagination.PaginatedResponse;
import org.example.techtalksskillbasedrecruitment.common.pagination.PaginationMeta;
import org.example.techtalksskillbasedrecruitment.modules.company.dto.request.CompanyRequest;
import org.example.techtalksskillbasedrecruitment.security.authorization.annotation.RecruiterOnly;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowCredentials = "false")
@RestController
@RequestMapping("/api/company")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/create")
    @RecruiterOnly
    public ResponseEntity<Company> createCompanyController(@RequestBody CompanyRequest companyRequest){
        Company newCompany = this.companyService.createCompanyService(companyRequest);
        return new ResponseEntity<>(newCompany, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    @RecruiterOnly
    public ResponseEntity<Company> updateCompanyController(@RequestParam Integer companyId, @RequestBody CompanyRequest companyRequest) {
         Company updatedCompany = this.companyService.updateCompanyService(companyId,companyRequest);
         return new ResponseEntity<>(updatedCompany,HttpStatus.OK);
    }

    @GetMapping("/getAll")
    @RecruiterOnly
    public ResponseEntity<PaginatedResponse<Company>> getAllCompaniesController(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit
    ) {

        Pageable pageable = PageRequest.of(page, limit);

        Page<Company> companyPage = this.companyService.getAllCompaniesService(pageable);

        PaginationMeta meta = new PaginationMeta(
                companyPage.getNumber(),
                companyPage.getSize(),
                companyPage.getTotalElements(),
                companyPage.getTotalPages(),
                companyPage.isFirst(),
                companyPage.isLast()
        );

        PaginatedResponse<Company> response =
                new PaginatedResponse<>(
                        companyPage.getContent(),
                        meta
                );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/companyId/{companyId}")
    @RecruiterOnly
    public ResponseEntity<Void> deleteCompanyController(@PathVariable Integer companyId){
        this.companyService.deleteCompanyService(companyId);
        return ResponseEntity.noContent().build();
    }
}
