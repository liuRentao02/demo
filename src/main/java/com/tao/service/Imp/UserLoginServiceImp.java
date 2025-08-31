package com.tao.service.Imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tao.common.LoginUser;
import com.tao.entity.pojo.User;
import com.tao.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

/**
 * UserLoginServiceImp - 用户登录服务实现类
 * 实现Spring Security的UserDetailsService接口，负责加载用户信息
 * 支持通过用户名或邮箱进行用户认证
 *
 * @author LiuRentao
 * @version 1.0
 * @since 2025/8/29 16:07
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserLoginServiceImp implements UserDetailsService {

    private final UserMapper userMapper;

    /**
     * 根据用户名或邮箱加载用户信息
     * 自动识别输入的是用户名还是邮箱地址，并查询对应的用户信息
     * 查询成功后创建LoginUser对象返回给Spring Security进行认证
     *
     * @param identifier 用户名或邮箱地址
     * @return UserDetails 用户详细信息对象
     * @throws UsernameNotFoundException 当用户不存在时抛出异常
     */
    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        User user;
        if (identifier.contains("@")) {
            // 按邮箱查找用户
            QueryWrapper<User> emailQuery = new QueryWrapper<>();
            emailQuery.eq("email", identifier);
            user = userMapper.selectOne(emailQuery);
        } else {
            // 按用户名查找用户
            QueryWrapper<User> usernameQuery = new QueryWrapper<>();
            usernameQuery.eq("username", identifier);
            user = userMapper.selectOne(usernameQuery);
        }

        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("用户名或邮箱不存在");
        }

        // 创建权限集合，将用户角色转换为Spring Security的GrantedAuthority
        Collection<? extends GrantedAuthority> authorities =
                Collections.singletonList(new SimpleGrantedAuthority((String) user.getRole()));

        // 返回适配后的用户信息对象
        return new LoginUser(user, authorities);
    }
}