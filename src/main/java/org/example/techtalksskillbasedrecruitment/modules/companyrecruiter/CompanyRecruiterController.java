package org.example.techtalksskillbasedrecruitment.modules.companyrecruiter;

import org.example.techtalksskillbasedrecruitment.modules.companyrecruiter.dto.request.CompanyRecruiterRequest;
import org.example.techtalksskillbasedrecruitment.modules.companyrecruiter.dto.response.CompanyRecruiterDTO;
import org.example.techtalksskillbasedrecruitment.modules.companyrecruiter.dto.response.CreateCompanyRecruiterResponse;
import org.example.techtalksskillbasedrecruitment.security.authorization.annotation.RecruiterOnly;
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

    @PostMapping("/create")
    @RecruiterOnly
    public ResponseEntity<CreateCompanyRecruiterResponse> createCompanyRecruiterResponse(@RequestBody CompanyRecruiterRequest companyRecruiter){
        CreateCompanyRecruiterResponse createCompanyRecruiterResponse = this.companyRecruiterService.createCompanyRecruiterService(companyRecruiter);
        return new ResponseEntity<CreateCompanyRecruiterResponse>(createCompanyRecruiterResponse, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    @RecruiterOnly
    public ResponseEntity<List<CompanyRecruiterDTO>> getCompanyRecruitersController() {
        List<CompanyRecruiterDTO> companyRecruiters =
                companyRecruiterService.getCompanyRecruitersService();
        return new ResponseEntity<>(companyRecruiters, HttpStatus.OK);
    }

    @DeleteMapping("/delete/crId/{crId}")
    @RecruiterOnly
    public ResponseEntity<Void> deleteCompanyRecruiterController(@PathVariable Integer crId){
        this.companyRecruiterService.deleteCompanyRecruiterService(crId);
        return ResponseEntity.noContent().build();
    }
}
