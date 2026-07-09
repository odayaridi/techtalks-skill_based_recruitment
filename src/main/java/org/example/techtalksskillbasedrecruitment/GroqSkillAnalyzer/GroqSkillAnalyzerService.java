package org.example.techtalksskillbasedrecruitment.GroqSkillAnalyzer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.techtalksskillbasedrecruitment.GroqSkillAnalyzer.dto.ExtractedSkillDTO;
import org.example.techtalksskillbasedrecruitment.exceptions.BadRequestException;
import org.example.techtalksskillbasedrecruitment.skill.Skill;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class GroqSkillAnalyzerService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    @Value("${groq.api.key}")
    private String groqApiKey;

    @Value("${groq.api.url}")
    private String groqApiUrl;

    @Value("${groq.model}")
    private String groqModel;

    public GroqSkillAnalyzerService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.webClient = WebClient.builder().build();
    }

    public List<ExtractedSkillDTO> analyzeProjectSkills(
            String projectName,
            String description,
            String githubUrl,
            List<Skill> skills
    ) {

        String skillCatalog = buildSkillCatalog(skills);

        String prompt = """
                Analyze the following software project.

                You may ONLY return skills that exist in the provided skill catalog.

                Skill Catalog:
                %s

                Project:
                Name: %s
                Description: %s
                GitHub URL: %s

                Return ONLY a valid JSON array.

                Format:

                [
                  {
                    "skillName":"Java",
                    "score":95
                  },
                  {
                    "skillName":"Spring Boot",
                    "score":90
                  }
                ]

                Rules:

                - Only return skills from the provided catalog.
                - Ignore skills that are not clearly demonstrated.
                - score must be between 0 and 100.
                - score represents confidence that the project demonstrates practical experience with the skill.
                - Do not invent skills.
                - Do not include explanations.
                """.formatted(
                skillCatalog,
                projectName,
                description,
                githubUrl
        );

        return callGroq(prompt);
    }

    public List<ExtractedSkillDTO> analyzeCertificateSkills(
            String certificateName,
            String issuedBy,
            List<Skill> skills
    ) {

        String skillCatalog = buildSkillCatalog(skills);

        String prompt = """
                Analyze this professional certification.

                Only return skills from the provided catalog.

                Skill Catalog:
                %s

                Certificate:
                Name: %s
                Issued By: %s

                Return ONLY a valid JSON array.

                Format:

                [
                  {
                    "skillName":"Java",
                    "score":90
                  }
                ]

                Rules:

                - Only return skills from the provided catalog.
                - Ignore skills that are not clearly demonstrated.
                - score must be between 0 and 100.
                - score represents confidence that the certification demonstrates practical experience with the skill.
                - Do not invent skills.
                - Do not include explanations.
                """.formatted(
                skillCatalog,
                certificateName,
                issuedBy
        );

        return callGroq(prompt);
    }

    private String buildSkillCatalog(List<Skill> skills) {

        return skills.stream()
                .map(skill -> "- " + skill.getSkillName())
                .toList()
                .toString();
    }

    private List<ExtractedSkillDTO> callGroq(String userPrompt) {

        Map<String, Object> requestBody = Map.of(
                "model", groqModel,
                "messages", List.of(
                        Map.of(
                                "role", "system",
                                "content", "You are a strict skill extraction engine. Return only valid JSON. Never include skills outside the provided skill catalog."
                        ),
                        Map.of(
                                "role", "user",
                                "content", userPrompt
                        )
                ),
                "temperature", 0,
                "max_tokens", 500
        );

        try {

            Map response = webClient.post()
                    .uri(groqApiUrl)
                    .header("Authorization", "Bearer " + groqApiKey)
                    .header("Content-Type", "application/json")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            Map choice = (Map) ((List<?>) response.get("choices")).get(0);
            Map message = (Map) choice.get("message");

            String content = message.get("content").toString();

            System.out.println("GROQ CONTENT = " + content);

            return objectMapper.readValue(
                    content,
                    new TypeReference<List<ExtractedSkillDTO>>() {
                    }
            );

        } catch (Exception e) {

            System.out.println("GROQ Err = " + e);
            throw new BadRequestException("Failed to analyze skills using Groq");
        }
    }
}