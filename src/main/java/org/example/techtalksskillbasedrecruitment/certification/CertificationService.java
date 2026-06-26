package org.example.techtalksskillbasedrecruitment.certification;

import org.example.techtalksskillbasedrecruitment.candidateprofile.CandidateProfile;
import org.example.techtalksskillbasedrecruitment.candidateprofile.CandidateProfileRepository;
import org.example.techtalksskillbasedrecruitment.certification.dto.request.CreateCertificationRequest;
import org.example.techtalksskillbasedrecruitment.certification.dto.response.CertificationResponse;
import org.example.techtalksskillbasedrecruitment.exceptions.ConflictException;
import org.example.techtalksskillbasedrecruitment.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

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

}
