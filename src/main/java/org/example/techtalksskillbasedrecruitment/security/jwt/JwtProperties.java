//package org.example.techtalksskillbasedrecruitment.security.jwt;
//
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.stereotype.Component;
//
//@Component
//@ConfigurationProperties(prefix = "jwt")
//public class JwtProperties {
//
//    private String secret;
//    private long expirationMs;
//
//    public String getSecret() {
//        return secret;
//    }
//
//    public void setSecret(String secret) {
//        this.secret = secret;
//    }
//
//    public long getExpirationMs() {
//        return expirationMs;
//    }
//
//    public void setExpirationMs(long expirationMs) {
//        this.expirationMs = expirationMs;
//    }
//}



//
//package org.example.techtalksskillbasedrecruitment.security.jwt;
//
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.stereotype.Component;
//
//@Component
//@ConfigurationProperties(prefix = "jwt")
//public class JwtProperties {
//
//    private String secret;
//    private long accessTokenExpirationMs;
//    private long refreshTokenExpirationMs;
//
//    public String getSecret() {
//        return secret;
//    }
//
//    public void setSecret(String secret) {
//        this.secret = secret;
//    }
//
//    public long getAccessTokenExpirationMs() {
//        return accessTokenExpirationMs;
//    }
//
//    public void setAccessTokenExpirationMs(long accessTokenExpirationMs) {
//        this.accessTokenExpirationMs = accessTokenExpirationMs;
//    }
//
//    public long getRefreshTokenExpirationMs() {
//        return refreshTokenExpirationMs;
//    }
//
//    public void setRefreshTokenExpirationMs(long refreshTokenExpirationMs) {
//        this.refreshTokenExpirationMs = refreshTokenExpirationMs;
//    }
//}






package org.example.techtalksskillbasedrecruitment.security.jwt;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    @NotBlank(message = "JWT secret must not be empty")
    private String secret;

    @Min(
            value = 60_000,
            message = "Access token expiration must be at least 60 seconds"
    )
    private long accessTokenExpirationMs;

    @Min(
            value = 60_000,
            message = "Refresh token expiration must be at least 60 seconds"
    )
    private long refreshTokenExpirationMs;

    /*
     * Default password-reset lifetime: 15 minutes.
     */
    @Min(
            value = 60_000,
            message = "Password-reset token expiration must be at least 60 seconds"
    )
    private long passwordResetExpirationMs = 900_000L;

    public JwtProperties() {
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getAccessTokenExpirationMs() {
        return accessTokenExpirationMs;
    }

    public void setAccessTokenExpirationMs(
            long accessTokenExpirationMs
    ) {
        this.accessTokenExpirationMs = accessTokenExpirationMs;
    }

    public long getRefreshTokenExpirationMs() {
        return refreshTokenExpirationMs;
    }

    public void setRefreshTokenExpirationMs(
            long refreshTokenExpirationMs
    ) {
        this.refreshTokenExpirationMs = refreshTokenExpirationMs;
    }

    public long getPasswordResetExpirationMs() {
        return passwordResetExpirationMs;
    }

    public void setPasswordResetExpirationMs(
            long passwordResetExpirationMs
    ) {
        this.passwordResetExpirationMs =
                passwordResetExpirationMs;
    }
}