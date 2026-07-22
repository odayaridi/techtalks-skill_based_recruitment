//package org.example.techtalksskillbasedrecruitment.modules.user.dto.response;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.Setter;
//
//@AllArgsConstructor
//@Getter
//@Setter
//public class LoginResponse {
//
//    private Integer userId;
//    private String username;
//    private String email;
//    private String phoneNumber;
//    private String roleName;
//    private String token;
//
//}

package org.example.techtalksskillbasedrecruitment.modules.user.dto.response;

public class LoginResponse {

    private Integer userId;
    private String username;
    private String email;
    private String phoneNumber;
    private String roleName;
    private String accessToken;
    private String refreshToken;

    public LoginResponse() {
    }

    public LoginResponse(Integer userId, String username, String email,
                         String phoneNumber, String roleName,
                         String accessToken, String refreshToken) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.roleName = roleName;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}