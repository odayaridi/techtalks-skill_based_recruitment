package org.example.techtalksskillbasedrecruitment.companyrecruiter;

import org.example.techtalksskillbasedrecruitment.companyrecruiter.dto.request.CompanyRecruiterRequest;
import org.example.techtalksskillbasedrecruitment.companyrecruiter.dto.response.CompanyRecruiterDTO;
import org.example.techtalksskillbasedrecruitment.companyrecruiter.dto.response.CreateCompanyRecruiterResponse;
import org.example.techtalksskillbasedrecruitment.security.annotation.RecruiterOnly;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/*
 delete
 */
@CrossOrigin(origins = "*", allowCredentials = "false")
@RestController
@RequestMapping("/api/company-recruiter")
public class CompanyRecruiterController {
    private final CompanyRecruiterService companyRecruiterService;


    public CompanyRecruiterController(CompanyRecruiterService companyRecruiterService) {
        this.companyRecruiterService = companyRecruiterService;
    }
     @RecruiterOnly
    @PostMapping("/create")
    public ResponseEntity<CreateCompanyRecruiterResponse> createCompanyRecruiterResponse(@RequestBody CompanyRecruiterRequest companyRecruiter){
        CreateCompanyRecruiterResponse createCompanyRecruiterResponse = this.companyRecruiterService.createCompanyRecruiterService(companyRecruiter);
        return new ResponseEntity<CreateCompanyRecruiterResponse>(createCompanyRecruiterResponse, HttpStatus.CREATED);
    }
    @RecruiterOnly
    @GetMapping("/getAll")
    public ResponseEntity<List<CompanyRecruiterDTO>> getCompanyRecruitersController() {
        List<CompanyRecruiterDTO> companyRecruiters =
                companyRecruiterService.getCompanyRecruitersService();
        return new ResponseEntity<>(companyRecruiters, HttpStatus.OK);
    }
    @RecruiterOnly
    @DeleteMapping("/delete/crId/{crId}")
    public ResponseEntity<Void> deleteCompanyRecruiterController(@PathVariable Integer crId){
        this.companyRecruiterService.deleteCompanyRecruiterService(crId);
        return ResponseEntity.noContent().build();
    }
}
