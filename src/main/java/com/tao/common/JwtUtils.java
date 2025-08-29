package com.tao.common;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
//生成和解析密钥的
@Component
public class JwtUtils {

    // 使用固定的密钥字符串
    private static final String FIXED_SECRET = "myFixedSuperSecretKeyForJWT123!@#$%^&*()";

    private SecretKey jwtSecret;
    private final long jwtExpirationMs = 24 * 60 * 60 * 1000;

    @PostConstruct
    public void init() {
        // 初始化时固定密钥，确保每次启动都一样
        byte[] keyBytes = Base64.getEncoder().encode(FIXED_SECRET.getBytes());
        this.jwtSecret = Keys.hmacShaKeyFor(keyBytes);
        System.out.println("jwtExpirationMs: " + jwtExpirationMs);
        System.out.println("JWT密钥初始化完成");
    }

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