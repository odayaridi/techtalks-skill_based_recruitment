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




package org.example.techtalksskillbasedrecruitment.security.jwt;

import io.jsonwebtoken.Claims;
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
    private static final String PASSWORD_RESET_TOKEN_TYPE = "PASSWORD_RESET";

    private final JwtProperties jwtProperties;

    public JwtService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(String username) {
        return generateToken(username, jwtProperties.getAccessTokenExpirationMs(), ACCESS_TOKEN_TYPE);
    }

    public String generateRefreshToken(String username) {
        return generateToken(username, jwtProperties.getRefreshTokenExpirationMs(), REFRESH_TOKEN_TYPE);
    }

    public String generatePasswordResetToken(String username) {
        return generateToken(username, jwtProperties.getPasswordResetTokenExpirationMs(), PASSWORD_RESET_TOKEN_TYPE);
    }

    private String generateToken(String username, long expirationMs, String type) {
        Date issuedAt = new Date();
        Date expiration = new Date(issuedAt.getTime() + expirationMs);
        return Jwts.builder()
                .setSubject(username)
                .claim(TOKEN_TYPE_CLAIM, type)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String extractTokenType(String token) {
        return extractClaim(token, claims -> claims.get(TOKEN_TYPE_CLAIM, String.class));
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean isAccessToken(String token) {
        try {
            return ACCESS_TOKEN_TYPE.equals(extractTokenType(token));
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    public boolean isRefreshToken(String token) {
        try {
            return REFRESH_TOKEN_TYPE.equals(extractTokenType(token));
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    public boolean validateAccessToken(String token, String username) {
        String tokenUsername = extractUsername(token);
        return tokenUsername.equals(username) && !isTokenExpired(token) && isAccessToken(token);
    }

    public boolean validateRefreshToken(String token, String username) {
        String tokenUsername = extractUsername(token);
        return tokenUsername.equals(username) && !isTokenExpired(token) && isRefreshToken(token);
    }

    public boolean isPasswordResetToken(String token) {
        try {
            return PASSWORD_RESET_TOKEN_TYPE.equals(extractTokenType(token));
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    public boolean validatePasswordResetToken(String token, String username) {
        String tokenUsername = extractUsername(token);
        return tokenUsername.equals(username) && !isTokenExpired(token) && isPasswordResetToken(token);
    }
}