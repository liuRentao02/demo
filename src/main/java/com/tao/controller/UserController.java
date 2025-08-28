package com.tao.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    // 普通用户和管理员都可以访问（role=0或1）
    @GetMapping("/profile")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')") // 用户或管理员都可以
    public String userProfile() {
        return "用户个人资料页面";
    }

    // 只有普通用户可以访问（role=1）
    @GetMapping("/content")
    @PreAuthorize("hasRole('USER')") // 只有普通用户
    public String userContent() {
        return "这是普通用户的专属内容";
    }
}