package org.example.techtalksskillbasedrecruitment.certification;

import org.example.techtalksskillbasedrecruitment.certification.dto.request.CreateCertificationRequest;
import org.example.techtalksskillbasedrecruitment.certification.dto.response.CertificationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowCredentials = "false")
@RestController
@RequestMapping("/api/certification")
public class CertificationController {
    private final CertificationService certificationService;


    public CertificationController(CertificationService certificationService) {
        this.certificationService = certificationService;
    }


    @PostMapping("/create")
    public ResponseEntity<CertificationResponse> createCertificationController(@RequestBody CreateCertificationRequest certificationRequest) {
        CertificationResponse certificationResponse = this.certificationService.createCertificationService(certificationRequest);
        return new ResponseEntity<CertificationResponse>(certificationResponse, HttpStatus.CREATED);
    }
}
