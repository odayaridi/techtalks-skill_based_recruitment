package org.example.techtalksskillbasedrecruitment.modules.job.mapper;

import org.example.techtalksskillbasedrecruitment.modules.job.Job;
import org.example.techtalksskillbasedrecruitment.modules.job.dto.response.JobResponse;
import org.springframework.stereotype.Component;

@Component
public class JobMapper {

    public JobResponse toJobResponseDTO(Job job) {
        return new JobResponse(
                job.getJobId(),
                job.getCompany().getCompanyName(),
                job.getCreatedBy().getUserId(),
                job.getTitle(),
                job.getDescription(),
                job.getJobType(),
                job.getLocation(),
                job.getStatus()
        );
    }
}