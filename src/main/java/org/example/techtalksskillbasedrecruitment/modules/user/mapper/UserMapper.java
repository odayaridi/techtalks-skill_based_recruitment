package org.example.techtalksskillbasedrecruitment.modules.user.mapper;

import org.example.techtalksskillbasedrecruitment.modules.user.User;
import org.example.techtalksskillbasedrecruitment.modules.user.dto.response.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toUserResponseDTO(User user) {
        return new UserResponse(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRole().getRoleId(),
                user.getRole().getRoleName(),
                user.getCreatedAt()
        );
    }
}