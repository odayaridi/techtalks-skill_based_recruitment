package org.example.techtalksskillbasedrecruitment.certification.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateCertificationRequest {

    @NotNull(message = "Candidate id is required")
    private Integer candidateId;


    @NotBlank(message = "Certificate name is required")
    private String certificateName;


    @NotBlank(message = "Issued by is required")
    private String issuedBy;


    @NotBlank(message = "Certificate file is required")
    private String certificateFile;


    @NotNull(message = "Certificate date is required")
    private LocalDate certificateDate;
}