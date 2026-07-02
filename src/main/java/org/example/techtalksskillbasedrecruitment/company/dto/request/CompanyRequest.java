package org.example.techtalksskillbasedrecruitment.company.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyRequest {
    private String companyName;
    private String description;
    private String location;
    private String website;
    private String logoPath;

    public CompanyRequest() {}
}
