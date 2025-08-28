package com.tao.entity.vo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Login
 *
 * @author LiuRentao
 * @version 1.0
 * @since 2025/8/28 15:35
 */
@AllArgsConstructor
@NoArgsConstructor
public class Login {
    public String name;
    public String password;

    @Override
    public String toString() {
        return "Login{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
