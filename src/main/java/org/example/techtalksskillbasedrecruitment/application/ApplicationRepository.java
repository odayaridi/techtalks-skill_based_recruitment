package org.example.techtalksskillbasedrecruitment.application;

import org.example.techtalksskillbasedrecruitment.application.dto.response.CandidateAppsResponse;
import org.example.techtalksskillbasedrecruitment.application.dto.response.MyApplicationsResponse;
import org.example.techtalksskillbasedrecruitment.candidateprofile.CandidateProfile;
import org.example.techtalksskillbasedrecruitment.job.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application,Integer> {
    Application findByJobAndCandidate(Job job, CandidateProfile candidateProfile);
    List<Application> findByJob(Job job);
    @Query("""
    SELECT new org.example.techtalksskillbasedrecruitment.application.dto.response.MyApplicationsResponse(
        a.applicationId, 
        a.status, 
        j.title, 
        j.description, 
        j.jobType, 
        j.location, 
        c.companyName, 
        c.location, 
        c.logoPath
    )
    FROM Application a 
    JOIN a.job j 
    JOIN j.company c
    WHERE a.candidate.candidateId = :candidateId
""")
    List<MyApplicationsResponse> fetchMyApps(@Param("candidateId") Integer candidateId);


    @Query("""
        SELECT new org.example.techtalksskillbasedrecruitment.application.dto.response.CandidateAppsResponse(
            a.applicationId,
            a.status,
            a.appliedAt,
            cp.location,
            cp.bio,
            cp.linkedinUrl,
            cp.githubUrl,
            u.username,
            u.email,
            u.phoneNumber
        )
        FROM Application a
        JOIN a.candidate cp
        JOIN cp.user u
        WHERE a.job.jobId = :jobId
    """)
    List<CandidateAppsResponse> findCandidatesAppsRepo(@Param("jobId") Integer jobId);

}
