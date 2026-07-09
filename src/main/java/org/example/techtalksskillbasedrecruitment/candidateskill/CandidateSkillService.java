package org.example.techtalksskillbasedrecruitment.candidateskill;

import org.example.techtalksskillbasedrecruitment.candidateprofile.CandidateProfile;
import org.example.techtalksskillbasedrecruitment.candidateprofile.CandidateProfileRepository;
import org.example.techtalksskillbasedrecruitment.candidateskill.dto.request.CreateCandidateSkillRequest;
import org.example.techtalksskillbasedrecruitment.candidateskill.dto.request.UpdateCandidateSkillRequest;
import org.example.techtalksskillbasedrecruitment.candidateskill.dto.response.CandidateSkillResponse;
import org.example.techtalksskillbasedrecruitment.candidateskill.mapper.CandidateSkillMapper;
import org.example.techtalksskillbasedrecruitment.exceptions.ResourceNotFoundException;
import org.example.techtalksskillbasedrecruitment.skill.Skill;
import org.example.techtalksskillbasedrecruitment.skill.SkillRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateSkillService {
    private final CandidateSkillRepository candidateSkillRepository;
    private final CandidateProfileRepository candidateProfileRepository;
    private final SkillRepository skillRepository;
    private final CandidateSkillMapper candidateSkillMapper;

    public CandidateSkillService(CandidateSkillRepository candidateSkillRepository, CandidateProfileRepository candidateProfileRepository, SkillRepository skillRepository, CandidateSkillMapper candidateSkillMapper) {
        this.candidateSkillRepository = candidateSkillRepository;
        this.candidateProfileRepository = candidateProfileRepository;
        this.skillRepository = skillRepository;
        this.candidateSkillMapper = candidateSkillMapper;
    }


    public CandidateSkillResponse addCandidateSkillService(CreateCandidateSkillRequest createCandidateSkillRequest){
        CandidateProfile candidateProfile = this.candidateProfileRepository.findById(createCandidateSkillRequest.getCandidateId()).orElseThrow(()->new ResourceNotFoundException("Invalid Candidate Id. Cannot add a candidate skill"));
        Skill skill = this.skillRepository.findBySkillName(createCandidateSkillRequest.getSkillName());
        if(skill == null){
            throw new ResourceNotFoundException("Skill not found to assign it to a candidate");
        }
        CandidateSkill candidateSkill = new CandidateSkill();
        candidateSkill.setCandidate(candidateProfile);
        candidateSkill.setSkill(skill);
        candidateSkill.setLevel(createCandidateSkillRequest.getLevel());
        candidateSkill.setYearsOfExperience(createCandidateSkillRequest.getYearsOfExperience());
        CandidateSkill newCandidateSkill = this.candidateSkillRepository.save(candidateSkill);
        return this.candidateSkillMapper.toDTOResponse(newCandidateSkill);
    }


    public List<CandidateSkillResponse> getCandidateSkillsService(Integer candidateId) {

        CandidateProfile candidateProfile = candidateProfileRepository.findById(candidateId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Invalid Candidate Id. Cannot fetch candidate skills"
                ));

        List<CandidateSkill> candidateSkillList =
                candidateSkillRepository.findByCandidate(candidateProfile);

        return candidateSkillList.stream()
                .map(this.candidateSkillMapper::toDTOResponse)
                .toList();
    }


    public CandidateSkillResponse updateCandidateSkillService(Integer candidateSkillId, UpdateCandidateSkillRequest candidateSkillRequest) {
        CandidateSkill candidateSkill = this.candidateSkillRepository.findById(candidateSkillId).orElseThrow(()->new ResourceNotFoundException("Invalid Candidate Skill Id, Cannot update candidate skill details"));
        Skill skill = this.skillRepository.findBySkillName(candidateSkillRequest.getSkillName());
        if(skill == null){
            throw new ResourceNotFoundException("Skill not found to update it to a candidate");
        }
        candidateSkill.setLevel(candidateSkillRequest.getLevel());
        candidateSkill.setSkill(skill);
        candidateSkill.setYearsOfExperience(candidateSkillRequest.getYearsOfExperience());
        CandidateSkill updatedCandidate = this.candidateSkillRepository.save(candidateSkill);
        return this.candidateSkillMapper.toDTOResponse(updatedCandidate);
    }

    public void deleteCandidateService(Integer candidateSkillId){
        CandidateSkill candidateSkill = this.candidateSkillRepository.findById(candidateSkillId).orElseThrow(()->new ResourceNotFoundException("Invalid Candidate Skill Id, Cannot delete candidate skill"));
        this.candidateSkillRepository.delete(candidateSkill);
    }
}
