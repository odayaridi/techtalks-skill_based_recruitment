package org.example.techtalksskillbasedrecruitment.certification;

import java.util.ArrayList;

import jakarta.transaction.Transactional;
import org.example.techtalksskillbasedrecruitment.candidateprofile.CandidateProfile;
import org.example.techtalksskillbasedrecruitment.candidateprofile.CandidateProfileRepository;
import org.example.techtalksskillbasedrecruitment.certification.dto.request.CreateCertificationRequest;
import org.example.techtalksskillbasedrecruitment.certification.dto.response.CertificationResponse;
import org.example.techtalksskillbasedrecruitment.exceptions.ConflictException;
import org.example.techtalksskillbasedrecruitment.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertificationService {
    private final CertificationRepository certificationRepository;
    private final CandidateProfileRepository candidateProfileRepository;
    public CertificationService(CertificationRepository certificationRepository, CandidateProfileRepository candidateProfileRepository) {
        this.certificationRepository = certificationRepository;
        this.candidateProfileRepository = candidateProfileRepository;
    }


    public CertificationResponse createCertificationService(CreateCertificationRequest certificationRequest) {
        boolean certificationExists = this.certificationRepository.existsByCertificateFile(certificationRequest.getCertificateFile());
        if (certificationExists) {
            throw new ConflictException("Certification already exits, upload a new one");
        }
        CandidateProfile candidateProfile = this.candidateProfileRepository.findById(certificationRequest.getCandidateId()).
                orElseThrow(() -> new ResourceNotFoundException("User does not have a candidate account to upload a certificate"));
        Certification certification = new Certification();
        certification.setCertificateFile(certificationRequest.getCertificateFile());
        certification.setCertificateName(certificationRequest.getCertificateName());
        certification.setIssuedBy(certificationRequest.getIssuedBy());
        certification.setCandidate(candidateProfile);

        Certification newCertification = this.certificationRepository.save(certification);
        return new CertificationResponse(newCertification.getCertificateId(),newCertification.getCandidate().getCandidateId(),certification.getCertificateName(),newCertification.getIssuedBy(),newCertification.getCertificateFile());
    }
    public List<CertificationResponse> getCertificationsByCandidateIdService(Integer candidateId) {
        List<Certification> certifications=this.certificationRepository.findByCandidate_CandidateId(candidateId);
        List <CertificationResponse> certificationResponses=new ArrayList<>();
        for(Certification certification:certifications){
            CertificationResponse certificationResponse=new CertificationResponse(
                       certification.getCertificateId(),
                      certification.getCandidate().getCandidateId(),
                        certification.getCertificateName(),
                        certification.getIssuedBy(),
            certification.getCertificateFile());
            certificationResponses.add(certificationResponse);
        }
      return  certificationResponses;
    }
      @Transactional
       public  void deleteCertificationByIdService(Integer certificateid){
        Certification certification=this.certificationRepository.findById(certificateid).
                orElseThrow(()->new ResourceNotFoundException("Certification  is not found"));
        this.certificationRepository.delete(certification);
   }

}
