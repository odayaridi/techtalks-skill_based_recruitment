package org.example.techtalksskillbasedrecruitment.certification;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificationRepository extends JpaRepository<Certification,Integer> {
    boolean existsByCertificateFile(String certificateFile);
}
