package org.example.techtalksskillbasedrecruitment.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LoginResponse {

    private Integer userId;
    private String username;
    private String email;
    private String phoneNumber;
    private String roleName;
    private String token;

}