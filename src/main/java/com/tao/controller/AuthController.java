package com.tao.controller;

import com.tao.common.JwtUtil;
import com.tao.entity.pojo.User;
import com.tao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        try {
            String username = loginRequest.get("username");
            String password = loginRequest.get("password");

            System.out.println("登录请求: username=" + username + ", password=" + password);

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtil.generateJwtToken(authentication);

            Map<String, Object> response = new HashMap<>();
            response.put("token", jwt);
            response.put("message", "登录成功");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("登录失败: " + e.getMessage());
            return ResponseEntity.badRequest().body("登录失败: " + e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            if (userService.userExists(user.getUsername())) {
                return ResponseEntity.badRequest().body("用户名已存在");
            }

            // 验证角色代码有效性（现在是字符串类型）
            if (user.getRole() != null) {
                // 允许的角色值："0", "1", "2", null（null会使用默认值）
                if (!user.getRole().equals("0") && !user.getRole().equals("1") && !user.getRole().equals("2")) {
                    return ResponseEntity.badRequest().body("无效的角色代码，只能是0、1或2");
                }
            }

            userService.registerUser(user);

            return ResponseEntity.ok("注册成功");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("注册失败: " + e.getMessage());
        }
    }

    // 新增：管理员注册接口（只有管理员可以创建管理员账号）
    @PostMapping("/register/admin")
    public ResponseEntity<?> registerAdmin(@RequestBody User user) {
        try {
            // 检查当前用户是否是管理员
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));

            if (!isAdmin) {
                return ResponseEntity.status(403).body("权限不足，只有管理员可以创建管理员账号");
            }

            if (userService.userExists(user.getUsername())) {
                return ResponseEntity.badRequest().body("用户名已存在");
            }

            // 强制设置为管理员角色
            user.setRole("0");
            userService.registerUser(user);

            return ResponseEntity.ok("管理员账号注册成功");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("注册失败: " + e.getMessage());
        }
    }

    // 新增：带角色选择的注册接口
    @PostMapping("/register-with-role")
    public ResponseEntity<?> registerWithRole(@RequestBody Map<String, String> registerRequest) {
        try {
            String username = registerRequest.get("username");
            String password = registerRequest.get("password");
            String nickname = registerRequest.get("nickname");
            String role = registerRequest.get("role");

            if (userService.userExists(username)) {
                return ResponseEntity.badRequest().body("用户名已存在");
            }

            // 验证角色
            if (role != null && !role.equals("0") && !role.equals("1") && !role.equals("2")) {
                return ResponseEntity.badRequest().body("无效的角色代码，只能是0、1或2");
            }

            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setNickname(nickname);
            user.setRole(role); // 可以是null，会使用默认值

            userService.registerUser(user);

            return ResponseEntity.ok("注册成功");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("注册失败: " + e.getMessage());
        }
    }
}