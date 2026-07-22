package org.example.techtalksskillbasedrecruitment.modules.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class UserResponse {

    private Integer userId;
    private String username;
    private String email;
    private String phoneNumber;
    private Integer roleId;
    private String roleName;
    private LocalDateTime createdAt;
}

