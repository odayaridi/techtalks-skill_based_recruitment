package org.example.techtalksskillbasedrecruitment.modules.user.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest {
    private Integer userId;
    private String username;
    private String email;
    private String phoneNumber;
    private Integer roleId;

}
