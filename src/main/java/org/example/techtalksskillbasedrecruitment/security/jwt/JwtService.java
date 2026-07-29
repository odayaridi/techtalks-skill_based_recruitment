//package org.example.techtalksskillbasedrecruitment.security.jwt;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.stereotype.Service;
//
//import javax.crypto.SecretKey;
//import java.nio.charset.StandardCharsets;
//import java.util.Date;
//import java.util.function.Function;
//
//@Service
//public class JwtService {
//
//    private final JwtProperties jwtProperties;
//
//    public JwtService(JwtProperties jwtProperties) {
//        this.jwtProperties = jwtProperties;
//    }
//
//    private SecretKey getSigningKey() {
//        return Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
//    }
//
//    public String generateToken(String username) {
//        Date issuedAt = new Date();
//        Date expiration = new Date(issuedAt.getTime() + jwtProperties.getExpirationMs());
//        return Jwts.builder()
//                .setSubject(username)
//                .setIssuedAt(issuedAt)
//                .setExpiration(expiration)
//                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//    public String extractUsername(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }
//
//    public Date extractExpiration(String token) {
//        return extractClaim(token, Claims::getExpiration);
//    }
//
//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
//
//    private Claims extractAllClaims(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(getSigningKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//    public boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date());
//    }
//
//    public boolean validateToken(String token, String username) {
//        String tokenUsername = extractUsername(token);
//        return tokenUsername.equals(username) && !isTokenExpired(token);
//    }
//}






//
//package org.example.techtalksskillbasedrecruitment.security.jwt;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.JwtException;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.stereotype.Service;
//
//import javax.crypto.SecretKey;
//import java.nio.charset.StandardCharsets;
//import java.util.Date;
//import java.util.function.Function;
//
//@Service
//public class JwtService {
//
//    private static final String TOKEN_TYPE_CLAIM = "type";
//    private static final String ACCESS_TOKEN_TYPE = "ACCESS";
//    private static final String REFRESH_TOKEN_TYPE = "REFRESH";
//
//    private final JwtProperties jwtProperties;
//
//    public JwtService(JwtProperties jwtProperties) {
//        this.jwtProperties = jwtProperties;
//    }
//
//    private SecretKey getSigningKey() {
//        return Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
//    }
//
//    public String generateAccessToken(String username) {
//        return generateToken(username, jwtProperties.getAccessTokenExpirationMs(), ACCESS_TOKEN_TYPE);
//    }
//
//    public String generateRefreshToken(String username) {
//        return generateToken(username, jwtProperties.getRefreshTokenExpirationMs(), REFRESH_TOKEN_TYPE);
//    }
//
//    private String generateToken(String username, long expirationMs, String type) {
//        Date issuedAt = new Date();
//        Date expiration = new Date(issuedAt.getTime() + expirationMs);
//        return Jwts.builder()
//                .setSubject(username)
//                .claim(TOKEN_TYPE_CLAIM, type)
//                .setIssuedAt(issuedAt)
//                .setExpiration(expiration)
//                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//    public String extractUsername(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }
//
//    public Date extractExpiration(String token) {
//        return extractClaim(token, Claims::getExpiration);
//    }
//
//    public String extractTokenType(String token) {
//        return extractClaim(token, claims -> claims.get(TOKEN_TYPE_CLAIM, String.class));
//    }
//
//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
//
//    private Claims extractAllClaims(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(getSigningKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//    public boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date());
//    }
//
//    public boolean isAccessToken(String token) {
//        try {
//            return ACCESS_TOKEN_TYPE.equals(extractTokenType(token));
//        } catch (JwtException | IllegalArgumentException ex) {
//            return false;
//        }
//    }
//
//    public boolean isRefreshToken(String token) {
//        try {
//            return REFRESH_TOKEN_TYPE.equals(extractTokenType(token));
//        } catch (JwtException | IllegalArgumentException ex) {
//            return false;
//        }
//    }
//
//    public boolean validateAccessToken(String token, String username) {
//        String tokenUsername = extractUsername(token);
//        return tokenUsername.equals(username) && !isTokenExpired(token) && isAccessToken(token);
//    }
//
//    public boolean validateRefreshToken(String token, String username) {
//        String tokenUsername = extractUsername(token);
//        return tokenUsername.equals(username) && !isTokenExpired(token) && isRefreshToken(token);
//    }
//}




package org.example.techtalksskillbasedrecruitment.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String TOKEN_TYPE_CLAIM = "type";

    private static final String ACCESS_TOKEN_TYPE = "ACCESS";
    private static final String REFRESH_TOKEN_TYPE = "REFRESH";
    private static final String PASSWORD_RESET_TOKEN_TYPE =
            "PASSWORD_RESET";

    private final JwtProperties jwtProperties;
    private final SecretKey signingKey;

    public JwtService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.signingKey = createSigningKey(
                jwtProperties.getSecret()
        );
    }

    public String generateAccessToken(String username) {
        return generateToken(
                username,
                jwtProperties.getAccessTokenExpirationMs(),
                ACCESS_TOKEN_TYPE
        );
    }

    public String generateRefreshToken(String username) {
        return generateToken(
                username,
                jwtProperties.getRefreshTokenExpirationMs(),
                REFRESH_TOKEN_TYPE
        );
    }

    public String generatePasswordResetToken(String username) {
        return generateToken(
                username,
                jwtProperties.getPasswordResetExpirationMs(),
                PASSWORD_RESET_TOKEN_TYPE
        );
    }

    private String generateToken(
            String username,
            long expirationMs,
            String tokenType
    ) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException(
                    "JWT username must not be empty"
            );
        }

        Date issuedAt = new Date();

        Date expiration = new Date(
                issuedAt.getTime() + expirationMs
        );

        return Jwts.builder()
                .setSubject(username)
                .claim(TOKEN_TYPE_CLAIM, tokenType)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(
                        signingKey,
                        SignatureAlgorithm.HS256
                )
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(
                token,
                Claims::getSubject
        );
    }

    public Date extractExpiration(String token) {
        return extractClaim(
                token,
                Claims::getExpiration
        );
    }

    public String extractTokenType(String token) {
        return extractClaim(
                token,
                claims -> claims.get(
                        TOKEN_TYPE_CLAIM,
                        String.class
                )
        );
    }

    public <T> T extractClaim(
            String token,
            Function<Claims, T> claimsResolver
    ) {
        Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException(
                    "JWT token must not be empty"
            );
        }

        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token)
                .before(new Date());
    }

    public boolean isAccessToken(String token) {
        return hasTokenType(
                token,
                ACCESS_TOKEN_TYPE
        );
    }

    public boolean isRefreshToken(String token) {
        return hasTokenType(
                token,
                REFRESH_TOKEN_TYPE
        );
    }

    public boolean isPasswordResetToken(String token) {
        return hasTokenType(
                token,
                PASSWORD_RESET_TOKEN_TYPE
        );
    }

    private boolean hasTokenType(
            String token,
            String expectedTokenType
    ) {
        try {
            String actualTokenType =
                    extractTokenType(token);

            return expectedTokenType.equals(
                    actualTokenType
            );

        } catch (JwtException
                 | IllegalArgumentException exception) {

            return false;
        }
    }

    public boolean validateAccessToken(
            String token,
            String username
    ) {
        return validateToken(
                token,
                username,
                ACCESS_TOKEN_TYPE
        );
    }

    public boolean validateRefreshToken(
            String token,
            String username
    ) {
        return validateToken(
                token,
                username,
                REFRESH_TOKEN_TYPE
        );
    }

    public boolean validatePasswordResetToken(
            String token,
            String username
    ) {
        return validateToken(
                token,
                username,
                PASSWORD_RESET_TOKEN_TYPE
        );
    }

    private boolean validateToken(
            String token,
            String username,
            String expectedTokenType
    ) {
        try {
            String tokenUsername =
                    extractUsername(token);

            String tokenType =
                    extractTokenType(token);

            return username != null
                    && username.equals(tokenUsername)
                    && expectedTokenType.equals(tokenType)
                    && !isTokenExpired(token);

        } catch (ExpiredJwtException exception) {
            return false;

        } catch (JwtException
                 | IllegalArgumentException exception) {

            return false;
        }
    }

    public long getAccessTokenExpirationMs() {
        return jwtProperties
                .getAccessTokenExpirationMs();
    }

    public long getRefreshTokenExpirationMs() {
        return jwtProperties
                .getRefreshTokenExpirationMs();
    }

    public long getPasswordResetExpirationMs() {
        return jwtProperties
                .getPasswordResetExpirationMs();
    }

    private SecretKey createSigningKey(String secret) {
        if (secret == null || secret.isBlank()) {
            throw new IllegalStateException(
                    "JWT secret is not configured"
            );
        }

        byte[] keyBytes =
                secret.getBytes(StandardCharsets.UTF_8);

        if (keyBytes.length < 32) {
            throw new IllegalStateException(
                    "JWT secret must contain at least 32 bytes " +
                            "for HS256"
            );
        }

        return Keys.hmacShaKeyFor(keyBytes);
    }
}