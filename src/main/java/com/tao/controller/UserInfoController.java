package com.tao.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserInfoController {

    @GetMapping("/user-info")
    public Map<String, Object> getUserInfo() {
        // 获取当前登录用户的信息
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // 获取用户的权限列表
        String authorities = SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities()
                .stream()
                .map(auth -> auth.getAuthority())
                .collect(Collectors.joining(", "));

        Map<String, Object> result = new HashMap<>();
        result.put("username", username);
        result.put("authorities", authorities);
        result.put("message", "这是用户信息接口，所有登录用户都可以访问");

        return result;
    }
}