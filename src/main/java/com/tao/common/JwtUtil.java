package com.tao.common;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
//生成和解析密钥的
@Component
public class JwtUtil {

    // 使用安全的密钥生成方式
    private final SecretKey jwtSecret = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long jwtExpirationMs = 24 * 60 * 60 * 1000;

    public String generateJwtToken(org.springframework.security.core.Authentication authentication) {
        String username = authentication.getName();

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(jwtSecret, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {
            System.out.println("JWT 验证失败: " + e.getMessage());
            return false;
        }
    }
}