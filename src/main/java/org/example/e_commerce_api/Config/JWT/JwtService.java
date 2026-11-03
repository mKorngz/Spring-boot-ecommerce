package org.example.e_commerce_api.Config.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.example.e_commerce_api.Config.CustomUserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Slf4j
@Component
public class JwtService {
    private static final String AUTHORITIES_CLAIM = "authorities";

    @Value("${app.jwt.secret-key}")
    private String secretKey;

    @Value("${app.jwt.access-token-expiration}")
    private Long accessTokenExpiration;

    public String generateAccessToken(CustomUserDetails userDetails) {
        Date now = new Date();
        return Jwts.builder()
                .issuedAt(now)
                .subject(userDetails.getUsername())
                .expiration(new Date(now.getTime() + accessTokenExpiration))
                .claim(AUTHORITIES_CLAIM, userDetails.getAuthorities())
                .signWith(getSignInKey())
                .issuer("jwt.com")
                .compact();
    }

    public Long getExpiresInMs() {
        return accessTokenExpiration;
    }

    public Claims extractClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUserName(String token) {
        return extractClaims(token).getSubject();
    }

    public Date extractExpiration(String token) {
        return extractClaims(token).getExpiration();
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean isValidToken(String token) {
        try {
            return !isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException ex) {
            log.error(ex.getMessage());
            return false;
        }
    }

    //Get Signing key from secret key
    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
