package org.example.techtalksskillbasedrecruitment.ai;



import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.techtalksskillbasedrecruitment.ai.dto.request.Groqrequest;
import org.example.techtalksskillbasedrecruitment.ai.dto.request.Message;
import org.example.techtalksskillbasedrecruitment.ai.dto.response.DetectedSkill;
import org.example.techtalksskillbasedrecruitment.ai.dto.response.Groqresponse;
import org.example.techtalksskillbasedrecruitment.project.Project;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class GroqService {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    @Value("${groq.api.key}")
    private String apiKey;

    public GroqService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.restClient = RestClient.create();
    }

    public List<DetectedSkill> analyzeProject(Project project) throws Exception {

        String prompt = """
                Analyze this software project and detect technical skills.

                Project name: %s
                Description: %s
                GitHub URL: %s

                Return ONLY JSON array:
                [
                  {"skill":"Java","score":90}
                ]
                """.formatted(
                project.getProjectName(),
                project.getDescription(),
                project.getGithubUrl()
        );

        Groqrequest request = new Groqrequest();
        request.setModel("llama-3.3-70b-versatile");
        request.setMessages(List.of(new Message("user", prompt)));

        Groqresponse response = restClient.post()
                .uri("https://api.groq.com/openai/v1/chat/completions")
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .body(request)
                .retrieve()
                .body(Groqresponse.class);

        String content = response.getChoices().get(0).getMessage().getContent();

        return objectMapper.readValue(content, new TypeReference<List<DetectedSkill>>() {});
    }
}