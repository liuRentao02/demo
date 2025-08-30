package com.tao.entity.vo;

import com.tao.entity.pojo.User;

/**
 * UserVoToUser
 *
 * @author LiuRentao
 * @version 1.0
 * @since 2025/8/30 16:40
 */
public class UserVoToUser {

    /**
     * 将表单中的对象转化为数据库中存储的用户对象（剔除表单中的code）
     * @return User
     */
    public static User toUser(UserVo userVo) {

        //创建一个数据库中存储的对象
        User user = new User();

        //传值
        user.setUsername(userVo.getUsername());
        user.setPassword(userVo.getPassword());
        user.setEmail(userVo.getEmail());
        user.setNickname(userVo.getNickname());

        // 返回包装后的对象
        return user;
    }
}
