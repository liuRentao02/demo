package com.tao.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * LoginUser
 *
 * @author LiuRentao
 * @version 1.0
 * @since 2025/8/29 17:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser {
    private String username;
    private String password;
}
