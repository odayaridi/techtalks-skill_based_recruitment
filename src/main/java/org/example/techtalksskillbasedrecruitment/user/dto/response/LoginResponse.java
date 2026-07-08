package org.example.techtalksskillbasedrecruitment.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private Integer userId;
    private String username;
    private String email;
    private Integer roleId;
    private String roleName;
}
