package com.tao.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    // 只有管理员可以访问（role=0）
    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')") // 需要ROLE_ADMIN权限
    public String adminDashboard() {
        return "欢迎访问管理员仪表板！";
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public String manageUsers() {
        return "用户管理页面 - 只有管理员可以看到";
    }
}