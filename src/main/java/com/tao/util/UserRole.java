package com.tao.util;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("0", "管理员", "ROLE_ADMIN"),
    USER("1", "用户", "ROLE_USER"),
    OTHER("2", "其他", "ROLE_OTHER");

    private final String code;  // 字符类型的数字："0", "1", "2"
    private final String description;
    private final String authority;

    UserRole(String code, String description, String authority) {
        this.code = code;
        this.description = description;
        this.authority = authority;
    }

    // 根据code获取枚举
    public static UserRole fromCode(String code) {
        for (UserRole role : values()) {
            if (role.code.equals(code)) {
                return role;
            }
        }
        return USER; // 默认返回用户
    }

    // 根据code获取权限字符串
    public static String getAuthorityByCode(String code) {
        return fromCode(code).getAuthority();
    }

    // 检查是否是管理员
    public static boolean isAdmin(String roleCode) {
        return "0".equals(roleCode);
    }
}