package br.com.alexandre.digitalservices.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Date;

@Service
public class JwtService {

    //
    private final Logger logger = LoggerFactory.getLogger(JwtService.class);

    private static final String SECRET = "dev-secret-key-12345678901234567890123456789012"; // 32+ chars

    public JwtService() {}


    public String generateToken(String email, String role) {
        long now = System.currentTimeMillis();
        Date issuedAt = new Date(now);
        Date expiration = new Date(now + 3600_000); // 1 hora
        return Jwts.builder()
            .subject(email)
            .claim("role", role)
            .issuedAt(issuedAt)
            .expiration(expiration)
            .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()))
            .compact();
    }


    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(SECRET.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
        } catch (Exception e) {
            logger.warn("Invalid JWT token: {}", e.getMessage());
            return null;
        }
    }

    public String extractUsername(String token) {
        Claims claims = extractAllClaims(token);
        return claims != null ? claims.getSubject() : null;
    }

    public boolean isTokenValid(String token) {
        Claims claims = extractAllClaims(token);
        if (claims == null) return false;
        return claims.getExpiration() != null && claims.getExpiration().after(new Date());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username != null && username.equals(userDetails.getUsername()) && isTokenValid(token);
    }

    public String extractRole(String token) {
        Claims claims = extractAllClaims(token);
        return claims != null ? claims.get("role", String.class) : null;
    }
}
