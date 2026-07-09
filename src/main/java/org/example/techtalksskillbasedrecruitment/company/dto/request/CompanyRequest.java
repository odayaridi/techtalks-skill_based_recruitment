package org.example.techtalksskillbasedrecruitment.company.dto.request;


import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyRequest {


   @NotBlank(message = "Company name is required")
    private String companyName;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Website is required")
    private String website;

    @NotBlank(message = "Location is required")
    private String location;

    @NotBlank(message = "Logo Path is required")
    private String logoPath;

}
