package com.nurseit.orderproject.util;

import com.nurseit.orderproject.dto.LoginRequestDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
@RequiredArgsConstructor
public class JwtUtil {

    @Value("${jwt.secret-key}")
    private String secretKey;


    public Claims validateToken(final String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(getSignKey())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid JWT token", e);
        }
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(LoginRequestDto user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("user", user);
        return createToken(claims, user.getUsername());
    }

    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // 1 час
                .signWith(SignatureAlgorithm.HS256, getSignKey()) // Используем строку для ключа
                .compact();
    }

    private byte[] getSignKey() {
        return Base64.getDecoder().decode(secretKey); // Используем стандартный Base64 декодер
    }

    private Claims extractAllClaims(String token) {
        return null;
        /*
        Jwts
                .parser()
                .verifyWith(getSignKey())
                .parseSignedClaims(token)
                .getPayload()
                .build();

         */
    }


}
