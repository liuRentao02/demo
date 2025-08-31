package com.tao.common;

import com.tao.entity.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * LoginUser - Spring Security用户认证适配器
 * 将自定义User实体适配为Spring Security可识别的UserDetails对象
 * 在认证过程中作为用户信息的载体，包含用户基本信息和权限信息
 *
 * @author LiuRentao
 * @version 1.0
 * @since 2025/8/29 16:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser implements UserDetails {

    /**
     * 自定义用户实体对象，包含用户的基本信息
     */
    private User user;

    /**
     * 用户的权限集合，用于Spring Security的权限验证
     */
    private Collection<? extends GrantedAuthority> authorities;

    /**
     * 获取用户的登录标识（用户名）
     * Spring Security在认证过程中用于识别用户身份
     *
     * @return 用户名
     */
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * 获取用户的加密密码
     * Spring Security用于与用户输入的密码进行比对验证
     *
     * @return 加密后的密码
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * 获取用户的权限集合
     * 用于权限验证（如：hasAuthority('ROLE_ADMIN')）、方法级安全控制和URL访问控制
     *
     * @return 权限集合
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * 检查用户账户是否启用
     * 如果返回false，用户将无法登录
     * 实际应用中可根据user.getEnabled()字段动态返回
     *
     * @return 账户是否启用
     */
    @Override
    public boolean isEnabled() {
        return user.getEnabled() == 1;
    }

    /**
     * 检查用户密码是否未过期
     * 如果密码过期，可以强制用户修改密码
     *
     * @return 密码是否未过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 检查用户账户是否未被锁定
     * 用于防止暴力破解，多次登录失败后可锁定账户
     * 实际应用中可根据user.getLocked()字段动态返回
     *
     * @return 账户是否未锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 检查用户账户是否未过期
     * 可设置账户有效期，过期后无法登录
     *
     * @return 账户是否未过期
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
}