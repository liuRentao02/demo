package com.tao.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tao.entity.pojo.User;
import com.tao.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", username);
        User user = userMapper.selectOne(queryWrapper);

        if (user == null) {
            throw new UsernameNotFoundException("用户不存在: " + username);
        }

        System.out.println("加载用户: " + username + ", 角色代码: " + user.getRole());

        // 根据字符数字角色代码分配权限
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        if ("0".equals(user.getRole())) {
            // 管理员（role="0"）
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            System.out.println("分配权限: ROLE_ADMIN, ROLE_USER");
        } else if ("1".equals(user.getRole())) {
            // 普通用户（role="1"）
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            System.out.println("分配权限: ROLE_USER");
        } else if ("2".equals(user.getRole())) {
            // 其他角色（role="2"）
            authorities.add(new SimpleGrantedAuthority("ROLE_OTHER"));
            System.out.println("分配权限: ROLE_OTHER");
        } else {
            // 默认用户权限
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            System.out.println("分配默认权限: ROLE_USER");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }

    public void registerUser(User user) {
        // 如果role为空，设置默认值"1"（用户）
        if (user.getRole() == null) {
            user.setRole("1");
        }
        user.setId(null);
        userMapper.insert(user);
    }

    public boolean userExists(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", username);
        return userMapper.selectCount(queryWrapper) > 0;
    }

    // 检查用户是否是管理员
    public boolean isAdmin(String role) {
        return "0".equals(role);
    }

    // 根据用户名获取用户信息
    public User getUserByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", username);
        return userMapper.selectOne(queryWrapper);
    }
}