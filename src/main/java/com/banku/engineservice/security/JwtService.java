package com.banku.engineservice.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class JwtService {

    private static final String USER_ID_CLAIM = "userId";
    
    @Value("${jwt.secret}")
    private String secretKey;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String extractUsername(String token) {
        try {
            return extractClaim(token, Claims::getSubject);
        } catch (Exception e) {
            log.error("Error al extraer el username del token", e);
            return null;
        }
    }
    
    public static String extractUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        String userId = null;
        if (authentication != null && authentication.getDetails() instanceof Map) {
            Map<String, Object> details = (Map<String, Object>) authentication.getDetails();
            userId = (String) details.get(USER_ID_CLAIM);
        }
        
        if (userId == null && authentication != null) {
            userId = authentication.getName();
        }
        return userId;
    }

    public String extractUserId(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
                    
            Map<String, Object> extraClaims = claims.get("extraClaims", Map.class);
            if (extraClaims != null && extraClaims.containsKey(USER_ID_CLAIM)) {
                return (String) extraClaims.get(USER_ID_CLAIM);
            }
            
            return claims.get(USER_ID_CLAIM, String.class);
        } catch (Exception e) {
            log.error("Error al extraer el userId del token", e);
            return null;
        }
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claimsResolver.apply(claims);
        } catch (ExpiredJwtException e) {
            log.warn("Token expirado: {}", e.getMessage());
            throw e;
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Error al procesar el token JWT: {}", e.getMessage());
            throw e;
        }
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return !isTokenExpired(token);
        } catch (ExpiredJwtException e) {
            log.warn("Token expirado: {}", e.getMessage());
            return false;
        } catch (JwtException | IllegalArgumentException e) {
            log.error("Token inválido: {}", e.getMessage());
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        try {
            return extractClaim(token, Claims::getExpiration).before(new Date());
        } catch (Exception e) {
            log.error("Error al verificar si el token ha expirado", e);
            return true;
        }
    }
} 