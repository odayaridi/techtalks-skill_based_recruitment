package org.example.techtalksskillbasedrecruitment.modules.certification;

import org.example.techtalksskillbasedrecruitment.modules.certification.dto.request.CreateCertificationRequest;
import org.example.techtalksskillbasedrecruitment.modules.certification.dto.response.CertificationResponse;
import org.example.techtalksskillbasedrecruitment.security.authorization.annotation.CandidateOnly;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*", allowCredentials = "false")
@RestController
@RequestMapping("/api/certification")
public class CertificationController {
    private final CertificationService certificationService;


    public CertificationController(CertificationService certificationService) {
        this.certificationService = certificationService;
    }


    @PostMapping("/create")
    @CandidateOnly
    public ResponseEntity<CertificationResponse> createCertificationController(@RequestBody CreateCertificationRequest certificationRequest) {
        CertificationResponse certificationResponse = this.certificationService.createCertificationService(certificationRequest);
        return new ResponseEntity<CertificationResponse>(certificationResponse, HttpStatus.CREATED);
    }

    @GetMapping("/getAllByCId/{candidateId}")
    @CandidateOnly
    public ResponseEntity<List<CertificationResponse>> getCertificationsByCandidateIdController(@PathVariable Integer candidateId) {
        List<CertificationResponse> certificationResponses =
                 this.certificationService.getCertificationsByCandidateIdService(candidateId);
        return new ResponseEntity<>(certificationResponses,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{certificateId}")
    @CandidateOnly
    public ResponseEntity<Void> deleteCertificationByIdController(@PathVariable Integer certificateId, @RequestParam Integer candidateId) {
        this.certificationService.deleteCertificationByIdService(certificateId,candidateId);

        return ResponseEntity.noContent().build();
    }


}
