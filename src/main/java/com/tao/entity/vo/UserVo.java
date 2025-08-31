package com.tao.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserVo - 用户注册视图对象
 * 用于接收前端提交的注册表单数据，包含验证码字段
 *
 * @author LiuRentao
 * @version 1.0
 * @since 2025/8/30 16:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {

    /**
     * 用户名 - 用于登录的唯一标识
     */
    private String username;

    /**
     * 密码 - 用户登录密码
     */
    private String password;

    /**
     * 昵称 - 用户显示名称
     */
    private String nickname;

    /**
     * 邮箱 - 用于接收验证码和通知
     */
    private String email;

    /**
     * 验证码 - 邮箱收到的6位数字验证码
     */
    private String code;
}