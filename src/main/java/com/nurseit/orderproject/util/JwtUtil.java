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

    public String generateToken(Long userId, Set<Role> roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("user", userId);
        claims.put("roles", roles);
        return createToken(claims, userId);
    }

    private String createToken(Map<String, Object> claims, Long userId) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(String.valueOf(userId))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // 1 час
                .signWith(SignatureAlgorithm.HS256, getSignKey()) // Используем строку для ключа
                .compact();
    }

    private byte[] getSignKey() {
        return Base64.getDecoder().decode(secretKey); // Используем стандартный Base64 декодер
    }

    public String getUserIdFromToken(String token) {
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

}
