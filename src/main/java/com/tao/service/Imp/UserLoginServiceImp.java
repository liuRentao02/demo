package com.tao.service.Imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
 * UserLoginServiceImp
 *
 * @author LiuRentao
 * @version 1.0
 * @since 2025/8/29 16:07
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserLoginServiceImp implements UserDetailsService {

    private final UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOne(queryWrapper);
        if (Objects.isNull(user)) {
            throw new RuntimeException("用户名或密码错误");
        }
        Collection<? extends GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority((String) user.getRole()));
        return new LoginUser(user,authorities);
    }
}
