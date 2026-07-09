package org.example.techtalksskillbasedrecruitment.project.dto.response;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.techtalksskillbasedrecruitment.candidateprofile.CandidateProfile;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class ProjectResponse {

    private Integer projectId;


    private Integer candidateId;


    private String projectName;

    private String description;


    private LocalDate startDate;


    private LocalDate endDate;


    private String githubUrl;


    private String demoUrl;


    private LocalDateTime createdAt;
}
