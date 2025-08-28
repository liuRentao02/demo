package com.tao.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // 生成 admin123 的加密密码
        String password = "123456";
        String encodedPassword = encoder.encode(password);
        System.out.println("Password: " + password);
        System.out.println("Encoded: " + encodedPassword);

        // 验证密码
        boolean matches = encoder.matches(password, encodedPassword);
        System.out.println("Matches: " + matches);

        // 生成 test123 的加密密码
        String testPassword = "test123";
        String testEncoded = encoder.encode(testPassword);
        System.out.println("\nPassword: " + testPassword);
        System.out.println("Encoded: " + testEncoded);
    }
}