package com.nurseit.orderproject.util;

import com.nurseit.orderproject.dto.LoginRequestDto;
import com.nurseit.orderproject.entity.Role;
import com.nurseit.orderproject.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class JwtUtil {

    @Value("${jwt.secret-key}")
    private String secretKey;


    public Claims validateToken(final String token) {
        try {
            return getClaimsFromToken(token);
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

    public String generateToken(String username, Set<Role> roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("user", username);
        claims.put("roles", roles);
        return createToken(claims, username);
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

    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public List<String> getRolesFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        List<String> roles = ((List<?>) claims.get("roles")).stream()
                .map(role -> (String) ((java.util.Map<?, ?>) role).get("name"))
                .collect(Collectors.toList());
        return roles;
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(getSignKey())
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
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
