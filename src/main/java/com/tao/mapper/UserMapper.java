package com.tao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tao.entity.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * UserMapper - 用户数据访问接口
 * 扩展BaseMapper，提供用户相关的数据库操作
 *
 * @author LiuRentao
 * @version 1.0
 * @since 2025/8/29 00:07
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 插入用户信息
     *
     * @param user 用户对象
     * @return 插入结果
     */
    int insertUser(User user);

    /**
     * 根据邮箱查询用户列表
     *
     * @param email 邮箱地址
     * @return 用户列表
     */
    List<User> queryByEmail(String email);

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户对象
     */
    User selectByUsername(String username);

    /**
     * 根据邮箱查询单个用户
     *
     * @param email 邮箱地址
     * @return 用户对象
     */
    User selectByEmail(String email);
}