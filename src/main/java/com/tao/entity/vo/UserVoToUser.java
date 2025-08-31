package com.tao.entity.vo;

import com.tao.entity.pojo.User;

/**
 * UserVoToUser - 用户数据转换工具类
 * 负责将前端提交的用户注册信息转换为数据库存储的用户对象
 * 剔除表单中的验证码等临时信息，保留核心用户数据
 *
 * @author LiuRentao
 * @version 1.0
 * @since 2025/8/30 16:40
 */
public class UserVoToUser {

    /**
     * 将前端表单中的UserVo对象转化为数据库中存储的User对象
     * 剔除表单中的验证码等临时信息，只保留核心用户数据
     *
     * @param userVo 前端提交的用户注册信息
     * @return 数据库存储的用户对象
     */
    public static User toUser(UserVo userVo) {
        User user = new User();
        user.setUsername(userVo.getUsername());
        user.setPassword(userVo.getPassword()); // 后续会在Service层加密
        user.setEmail(userVo.getEmail());
        user.setNickname(userVo.getNickname());
        return user;
    }
}