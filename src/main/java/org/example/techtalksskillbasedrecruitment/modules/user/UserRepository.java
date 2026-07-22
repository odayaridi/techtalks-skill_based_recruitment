package org.example.techtalksskillbasedrecruitment.modules.user;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByEmailAndUserIdNot(String email, Integer userId);
    boolean existsByUsernameAndUserIdNot(String username, Integer userId);
    boolean existsByPhoneNumberAndUserIdNot(String phoneNumber, Integer userId);
}
