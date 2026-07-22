package org.example.techtalksskillbasedrecruitment.modules.certification;

import java.util.ArrayList;

import jakarta.transaction.Transactional;
import org.example.techtalksskillbasedrecruitment.modules.GroqSkillAnalyzer.GroqSkillAnalyzerService;
import org.example.techtalksskillbasedrecruitment.modules.GroqSkillAnalyzer.dto.ExtractedSkillDTO;
import org.example.techtalksskillbasedrecruitment.modules.candidateprofile.CandidateProfile;
import org.example.techtalksskillbasedrecruitment.modules.candidateprofile.CandidateProfileRepository;
import org.example.techtalksskillbasedrecruitment.modules.candidateskillscore.CandidateSkillScore;
import org.example.techtalksskillbasedrecruitment.modules.candidateskillscore.CandidateSkillScoreRepository;
import org.example.techtalksskillbasedrecruitment.modules.certification.dto.request.CreateCertificationRequest;
import org.example.techtalksskillbasedrecruitment.modules.certification.dto.response.CertificationResponse;
import org.example.techtalksskillbasedrecruitment.common.exceptions.ConflictException;
import org.example.techtalksskillbasedrecruitment.common.exceptions.ResourceNotFoundException;
import org.example.techtalksskillbasedrecruitment.modules.skill.Skill;
import org.example.techtalksskillbasedrecruitment.modules.skill.SkillRepository;
import org.example.techtalksskillbasedrecruitment.modules.skillevidence.SkillEvidence;
import org.example.techtalksskillbasedrecruitment.modules.skillevidence.SkillEvidenceRepository;
import org.example.techtalksskillbasedrecruitment.modules.skillevidence.dto.SkillAverageScoreDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertificationService {
    private final CertificationRepository certificationRepository;
    private final CandidateProfileRepository candidateProfileRepository;
    private final SkillEvidenceRepository skillEvidenceRepository;
    private final GroqSkillAnalyzerService groqSkillAnalyzerService;
    private final SkillRepository skillRepository;
    private final CandidateSkillScoreRepository candidateSkillScoreRepository;
    public CertificationService(CertificationRepository certificationRepository, CandidateProfileRepository candidateProfileRepository, SkillEvidenceRepository skillEvidenceRepository, GroqSkillAnalyzerService groqSkillAnalyzerService, SkillRepository skillRepository, CandidateSkillScoreRepository candidateSkillScoreRepository) {
        this.certificationRepository = certificationRepository;
        this.candidateProfileRepository = candidateProfileRepository;
        this.skillEvidenceRepository = skillEvidenceRepository;
        this.groqSkillAnalyzerService = groqSkillAnalyzerService;
        this.skillRepository = skillRepository;
        this.candidateSkillScoreRepository = candidateSkillScoreRepository;
    }

@Transactional
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
        certification.setCertificateDate(certificationRequest.getCertificateDate());
        certification.setIssuedBy(certificationRequest.getIssuedBy());
        certification.setCandidate(candidateProfile);
        String base64 = certificationRequest.getCertificateFile();
        certification.setCertificateFile(base64);
        Certification newCertification = this.certificationRepository.save(certification);


        List<Skill> skillsList = this.skillRepository.findAll();
        List<ExtractedSkillDTO> skillDTOS =  this.groqSkillAnalyzerService.analyzeCertificateSkills(certification.getCertificateName(),certification.getIssuedBy(),skillsList);
        for (ExtractedSkillDTO es : skillDTOS) {
            SkillEvidence skillEvidence = new SkillEvidence();
            skillEvidence.setReferenceId(newCertification.getCertificateId());
            skillEvidence.setEvidenceType("CERTIFICATION");
            Skill skill = this.skillRepository.findBySkillName(es.getSkillName());
            if(skill == null){
                throw new ResourceNotFoundException("Invalid score name. Not found");
            }
            skillEvidence.setSkill(skill);
            skillEvidence.setCandidate(candidateProfile);
            skillEvidence.setScore(es.getScore());
            this.skillEvidenceRepository.save(skillEvidence);
        }
        this.candidateSkillScoreRepository.deleteByCandidate(candidateProfile);
        this.candidateSkillScoreRepository.flush();
        List<SkillAverageScoreDto> skillAverageScoreDtos = this.skillEvidenceRepository.findAverageScorePerSkill(candidateProfile.getCandidateId());
        for (SkillAverageScoreDto sas : skillAverageScoreDtos) {
            CandidateSkillScore candidateSkillScore = new CandidateSkillScore();
            Skill skill = this.skillRepository.findById(sas.getSkillId()).orElseThrow(()->new ResourceNotFoundException("Skill not found"));
            candidateSkillScore.setSkill(skill);
            candidateSkillScore.setCandidate(candidateProfile);
            candidateSkillScore.setScore(sas.getAverageScore());
            this.candidateSkillScoreRepository.save(candidateSkillScore);
        }


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
       public  void deleteCertificationByIdService(Integer certificateid, Integer candidateId){

          CandidateProfile candidateProfile = this.candidateProfileRepository.findById(candidateId).
                  orElseThrow(() -> new ResourceNotFoundException("Invalid Candidate Id. Cannot delete the certification"));

        Certification certification=this.certificationRepository.findById(certificateid).
                orElseThrow(()->new ResourceNotFoundException("Certification  is not found"));


          // Delete all evidence generated by this project
          this.skillEvidenceRepository.deleteByCandidateAndEvidenceTypeAndReferenceId(
                  candidateProfile,
                  "CERTIFICATION",
                 certificateid
          );

          // Remove previous calculated scores
          this.candidateSkillScoreRepository.deleteByCandidate(candidateProfile);

          // Recalculate remaining scores
          List<SkillAverageScoreDto> averages =
                  this.skillEvidenceRepository.findAverageScorePerSkill(
                          candidateProfile.getCandidateId()
                  );

          for (SkillAverageScoreDto average : averages) {

              Skill skill = this.skillRepository.findById(average.getSkillId())
                      .orElseThrow(() ->
                              new ResourceNotFoundException("Skill not found."));

              CandidateSkillScore candidateSkillScore = new CandidateSkillScore();
              candidateSkillScore.setCandidate(candidateProfile);
              candidateSkillScore.setSkill(skill);
              candidateSkillScore.setScore(average.getAverageScore());

              this.candidateSkillScoreRepository.save(candidateSkillScore);
          }


        this.certificationRepository.delete(certification);
   }

}
