package org.example.techtalksskillbasedrecruitment.candidateskill;

import org.example.techtalksskillbasedrecruitment.candidateprofile.CandidateProfile;
import org.example.techtalksskillbasedrecruitment.candidateprofile.CandidateProfileRepository;
import org.example.techtalksskillbasedrecruitment.candidateskill.dto.request.CreateCandidateSkillRequest;
import org.example.techtalksskillbasedrecruitment.candidateskill.dto.response.CandidateSkillResponse;
import org.example.techtalksskillbasedrecruitment.exceptions.ResourceNotFoundException;
import org.example.techtalksskillbasedrecruitment.skill.Skill;
import org.example.techtalksskillbasedrecruitment.skill.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CandidateSkillService {
    private final CandidateSkillRepository candidateSkillRepository;
    private final CandidateProfileRepository candidateProfileRepository;
    private final SkillRepository skillRepository;

    public CandidateSkillService(CandidateSkillRepository candidateSkillRepository,
                                 CandidateProfileRepository candidateProfileRepository,
                                 SkillRepository skillRepository) {
        this.candidateSkillRepository = candidateSkillRepository;
        this.candidateProfileRepository = candidateProfileRepository;
        this.skillRepository = skillRepository;
    }

    public CandidateSkillResponse createCandidateSkillService(CreateCandidateSkillRequest request) {
        CandidateProfile candidateProfile = this.candidateProfileRepository.findById(request.getCandidateId())
                .orElseThrow(() -> new ResourceNotFoundException("Candidate with id " + request.getCandidateId() + " does not exist"));

        Skill skill = this.skillRepository.findBySkillName(request.getSkillName())
                .orElseThrow(() -> new ResourceNotFoundException("Skill with name '" + request.getSkillName() + "' does not exist"));

        CandidateSkill candidateSkill = new CandidateSkill();
        candidateSkill.setCandidate(candidateProfile);
        candidateSkill.setSkill(skill);
        candidateSkill.setLevel(request.getLevel());
        candidateSkill.setYearsOfExperience(request.getYearsOfExperience());

        CandidateSkill savedCandidateSkill = this.candidateSkillRepository.save(candidateSkill);

        return new CandidateSkillResponse(
                savedCandidateSkill.getCandidateSkillId(),
                savedCandidateSkill.getCandidate().getCandidateId(),
                savedCandidateSkill.getSkill().getSkillName(),
                savedCandidateSkill.getLevel(),
                savedCandidateSkill.getYearsOfExperience()
        );
    }
    public List<CandidateSkillResponse> getCandidateSkillsService(Integer candidateId) {
        CandidateProfile candidateProfile = this.candidateProfileRepository.findById(candidateId)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate with id " + candidateId + " does not exist"));

        List<CandidateSkill> candidateSkills = this.candidateSkillRepository.findByCandidate(candidateProfile);
        List<CandidateSkillResponse> candidateSkillResponses = new ArrayList<>();

        for (CandidateSkill candidateSkill : candidateSkills) {
            CandidateSkillResponse response = new CandidateSkillResponse(
                    candidateSkill.getCandidateSkillId(),
                    candidateSkill.getCandidate().getCandidateId(),
                    candidateSkill.getSkill().getSkillName(),
                    candidateSkill.getLevel(),
                    candidateSkill.getYearsOfExperience()
            );
            candidateSkillResponses.add(response);
        }

        return candidateSkillResponses;
    }
}