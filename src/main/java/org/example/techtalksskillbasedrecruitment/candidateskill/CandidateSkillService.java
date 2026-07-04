package org.example.techtalksskillbasedrecruitment.candidateskill;

import org.example.techtalksskillbasedrecruitment.candidateskill.dto.request.UpdateCandidateSkillRequest;
import org.example.techtalksskillbasedrecruitment.candidateskill.dto.response.CandidateSkillResponse;
import org.example.techtalksskillbasedrecruitment.exceptions.ResourceNotFoundException;
import org.example.techtalksskillbasedrecruitment.skill.Skill;
import org.example.techtalksskillbasedrecruitment.skill.SkillRepository;
import org.springframework.stereotype.Service;

@Service
public class CandidateSkillService {

    private final CandidateSkillRepository candidateSkillRepository;
    private final SkillRepository skillRepository;

    public CandidateSkillService(CandidateSkillRepository candidateSkillRepository,
                                 SkillRepository skillRepository) {
        this.candidateSkillRepository = candidateSkillRepository;
        this.skillRepository = skillRepository;
    }

    public CandidateSkillResponse updateCandidateSkill(Integer candidateSkillId,
                                                       UpdateCandidateSkillRequest request) {

        CandidateSkill candidateSkill = candidateSkillRepository.findById(candidateSkillId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Candidate skill not found with id: " + candidateSkillId));

        Skill skill = skillRepository.findBySkillName(request.getSkillName());
        if (skill == null) {
            throw new ResourceNotFoundException(
                    "Skill not found with name: " + request.getSkillName());
        }

        candidateSkill.setSkill(skill);
        candidateSkill.setLevel(request.getLevel());
        candidateSkill.setYearsOfExperience(request.getYearsOfExperience());

        CandidateSkill updated = candidateSkillRepository.save(candidateSkill);

        return mapToResponse(updated);
    }

    private CandidateSkillResponse mapToResponse(CandidateSkill candidateSkill) {
        return new CandidateSkillResponse(
                candidateSkill.getCandidateSkillId(),
                candidateSkill.getCandidate().getCandidateId(),
                candidateSkill.getSkill().getSkillName(),
                candidateSkill.getLevel(),
                candidateSkill.getYearsOfExperience()
        );
    }
    public void deleteCandidateService(Integer candidateSkillId) {

        CandidateSkill candidateSkill = candidateSkillRepository.findById(candidateSkillId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Candidate skill not found with id: " + candidateSkillId));

        candidateSkillRepository.delete(candidateSkill);
    }
}