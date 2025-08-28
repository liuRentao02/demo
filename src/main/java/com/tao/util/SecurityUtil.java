package com.tao.util;

import com.tao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

    @Autowired
    private UserService userService;

    // 检查当前用户是否是管理员（根据Spring Security权限）
    public boolean isAdmin() {
        return SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
    }

    // 检查当前用户是否有特定角色
    public boolean hasRole(String roleAuthority) {
        return SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(auth -> auth.getAuthority().equals(roleAuthority));
    }

    // 获取当前用户的角色代码（从数据库）
    public String getCurrentUserRoleCode() {
        String username = getCurrentUsername();
        com.tao.entity.pojo.User user = userService.getUserByUsername(username);
        return user != null ? user.getRole() : "1"; // 默认返回"1"
    }

    // 获取当前用户名
    public String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    // 检查是否是管理员（根据数据库角色字段）
    public boolean isAdminUser() {
        String roleCode = getCurrentUserRoleCode();
        return "0".equals(roleCode);
    }

    // 获取角色描述
    public String getCurrentUserRoleDescription() {
        String roleCode = getCurrentUserRoleCode();
        switch (roleCode) {
            case "0": return "管理员";
            case "1": return "用户";
            case "2": return "其他";
            default: return "用户";
        }
    }
}