package org.example.techtalksskillbasedrecruitment.matchscore;

import org.example.techtalksskillbasedrecruitment.ai.GroqService;
import org.example.techtalksskillbasedrecruitment.ai.dto.response.DetectedSkill;
import org.example.techtalksskillbasedrecruitment.application.Application;
import org.example.techtalksskillbasedrecruitment.application.ApplicationRepository;
import org.example.techtalksskillbasedrecruitment.candidateskillscore.CandidateSkillScore;
import org.example.techtalksskillbasedrecruitment.candidateskillscore.CandidateSkillScoreRepository;
import org.example.techtalksskillbasedrecruitment.certification.Certification;
import org.example.techtalksskillbasedrecruitment.certification.CertificationRepository;
import org.example.techtalksskillbasedrecruitment.exceptions.ResourceNotFoundException;
import org.example.techtalksskillbasedrecruitment.job.Job;
import org.example.techtalksskillbasedrecruitment.job.JobRepository;
import org.example.techtalksskillbasedrecruitment.jobskill.JobSkill;
import org.example.techtalksskillbasedrecruitment.jobskill.JobSkillRepository;
import org.example.techtalksskillbasedrecruitment.project.Project;
import org.example.techtalksskillbasedrecruitment.project.ProjectRepository;
import org.example.techtalksskillbasedrecruitment.skill.Skill;
import org.example.techtalksskillbasedrecruitment.skill.SkillRepository;
import org.example.techtalksskillbasedrecruitment.skillevidence.SkillEvidence;
import org.example.techtalksskillbasedrecruitment.skillevidence.SkillEvidenceRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MatchScoreService {
       private final JobRepository jobRepository;
    private final MatchScoreRepository matchScoreRepository;
    private  final JobSkillRepository jobSkillRepository;
    private  final ApplicationRepository applicationRepository;
    private  final ProjectRepository projectRepository;
    private  final CertificationRepository certificationRepository;
    private final SkillEvidenceRepository skillEvidenceRepository;
    private final SkillRepository skillRepository;
    private  final CandidateSkillScoreRepository candidateSkillScoreRepository;
    private final GroqService groqService;

       public  MatchScoreService(JobRepository jobRepository,
                                 MatchScoreRepository matchScoreRepository,
                                 JobSkillRepository jobSkillRepository,
                                 ApplicationRepository applicationRepository,
                                 ProjectRepository projectRepository,
                                 CertificationRepository certificationRepository,
                                 SkillEvidenceRepository skillEvidenceRepository,
                                 SkillRepository skillRepository,
                                 CandidateSkillScoreRepository candidateSkillScoreRepository,
                                 GroqService groqService){
           this.jobRepository=jobRepository;
           this.matchScoreRepository=matchScoreRepository;
           this.jobSkillRepository=jobSkillRepository;
           this.applicationRepository=applicationRepository;
           this.projectRepository= projectRepository;
           this.certificationRepository=certificationRepository;
           this.skillEvidenceRepository=skillEvidenceRepository;
           this.skillRepository=skillRepository;
           this.candidateSkillScoreRepository=candidateSkillScoreRepository;
           this.groqService=groqService;
       }

    public List<DetectedSkill> testGroqService(Integer projectId) throws Exception {
        Project project = this.projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project does not exist"));

        return this.groqService.analyzeProject(project);
    }

    public String analyzeJobService(Integer jobId) throws Exception {
        Job job = this.jobRepository.findById(jobId).orElseThrow(() ->
                new ResourceNotFoundException("Job does not exist"));

        List<JobSkill> jobSkills = this.jobSkillRepository.findByJob_JobId(jobId);


        List<Application> applications = this.applicationRepository.findByJob_JobId(jobId);
        String result = "Job found: " + job.getTitle()
                + ", required skills count: " + jobSkills.size()
                + ", applications count: " + applications.size();

        for (Application application : applications) {
            Integer candidateID = application.getCandidate().getCandidateId();

            List<Project> projects=this.projectRepository.findByCandidate_CandidateId(candidateID);
            List<Certification> certifications=this.certificationRepository.findByCandidate_CandidateId(candidateID);
            result += "\nCandidate id: " + candidateID
                    + ", projects count: " + projects.size()
                    + ", certifications count: " + certifications.size();
            for (Project project : projects) {
                result += "\nAnalyzing project: " + project.getProjectName();

                List<DetectedSkill> detectedSkills = this.groqService.analyzeProject(project);

                for (DetectedSkill detectedSkill : detectedSkills) {

                    Skill skill = this.skillRepository.findBySkillName(detectedSkill.getSkill());

                    if (skill == null) {
                        result += "\nSkill not found in DB: " + detectedSkill.getSkill();
                        continue;
                    }

                    SkillEvidence skillEvidence = new SkillEvidence();
                    skillEvidence.setCandidate(application.getCandidate());
                    skillEvidence.setSkill(skill);
                    skillEvidence.setEvidenceType("PROJECT");
                    skillEvidence.setReferenceId(project.getProjectId());
                    skillEvidence.setScore(detectedSkill.getScore());

                    this.skillEvidenceRepository.save(skillEvidence);

                    result += "\nSaved evidence: "
                            + detectedSkill.getSkill()
                            + " = "
                            + detectedSkill.getScore();
                }
            }
            for(Certification certification :certifications){
                result += "\nAnalyzing certification: " + certification.getCertificateName();

                Skill javaSkill = this.skillRepository.findBySkillName("Java");

                if (javaSkill == null) {
                    throw new ResourceNotFoundException("Java skill does not exist");
                }

                SkillEvidence skillEvidence = new SkillEvidence();
                skillEvidence.setCandidate(application.getCandidate());
                skillEvidence.setSkill(javaSkill);
                skillEvidence.setEvidenceType("CERTIFICATE");
                skillEvidence.setReferenceId(certification.getCertificateId());
                skillEvidence.setScore(95);

                this.skillEvidenceRepository.save(skillEvidence);

                result += "\nSaved evidence from certificate: Java = 95";

            }
            List<SkillEvidence> evidences =
                    this.skillEvidenceRepository.findByCandidate_CandidateId(candidateID);
            Map<Integer,List<Integer>> groupedScores=new HashMap<>();
            for(SkillEvidence evidence:evidences){
                Integer Skillid=evidence.getSkill().getSkillId();
                if(!groupedScores.containsKey(Skillid)){
                    groupedScores.put(Skillid,new ArrayList<>());
                }
                groupedScores.get(Skillid).add(evidence.getScore());
            }
            for (Map.Entry<Integer, List<Integer>> entry : groupedScores.entrySet()){
                Integer skillid=entry.getKey();
                 List<Integer> scores=entry.getValue();
                int total = 0;

                for (Integer score : scores) {
                    total += score;
                }

                double average = (double) total / scores.size();

                result += "\nSkill ID: " + skillid+ ", Average: " + average;

                Skill skill = this.skillRepository.findById(skillid)
                        .orElseThrow(() -> new ResourceNotFoundException("Skill does not exist"));

            CandidateSkillScore candidateSkillScore =
                        this.candidateSkillScoreRepository
                                .findByCandidate_CandidateIdAndSkill_SkillId(candidateID ,skillid)
                                .orElse(new CandidateSkillScore());

                candidateSkillScore.setCandidate(application.getCandidate());
                candidateSkillScore.setSkill(skill);
                candidateSkillScore.setScore(BigDecimal.valueOf(average));

                this.candidateSkillScoreRepository.save(candidateSkillScore);
            }

            double matchPercentage = 0;

            for (JobSkill jobSkill : jobSkills) {
                Integer skillId = jobSkill.getSkill().getSkillId();
                Integer weight = jobSkill.getWeight();

                CandidateSkillScore candidateSkillScore =
                        this.candidateSkillScoreRepository
                                .findByCandidate_CandidateIdAndSkill_SkillId(candidateID, skillId)
                                .orElse(null);

                if (candidateSkillScore != null) {
                    double skillScore = candidateSkillScore.getScore().doubleValue();
                    matchPercentage += skillScore * weight / 100;
                }
            }
            MatchScore matchScore = this.matchScoreRepository
                    .findByJob_JobIdAndCandidate_CandidateId(jobId, candidateID)
                    .orElse(new MatchScore());

            matchScore.setJob(job);
            matchScore.setCandidate(application.getCandidate());
            matchScore.setMatchPercentage(BigDecimal.valueOf(matchPercentage));

            this.matchScoreRepository.save(matchScore);

            result += "\nSaved match percentage: " + matchPercentage;



        }
        List<MatchScore> rankedMatches =
                this.matchScoreRepository.findByJob_JobIdOrderByMatchPercentageDesc(jobId);

        result += "\nRanked candidates:";

        for (MatchScore rankedMatch : rankedMatches) {
            result += "\nCandidate id: "
                    + rankedMatch.getCandidate().getCandidateId()
                    + ", match: "
                    + rankedMatch.getMatchPercentage();
        }




       return  result;
    }


}
