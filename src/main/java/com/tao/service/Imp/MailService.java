package com.tao.service.Imp;

import com.tao.entity.pojo.User;
import com.tao.entity.vo.UserVo;
import com.tao.entity.vo.UserVoToUser;
import com.tao.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpSession;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * MailService - 邮件服务类
 * 负责发送验证码邮件和处理用户注册逻辑
 * 包含验证码生成、邮件发送、注册验证等功能
 *
 * @author Slf4j
 * @Service
 * @Transactional
 */
@Slf4j
@Service
@Transactional
public class MailService {

    private final JavaMailSender mailSender;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    public MailService(JavaMailSender mailSender, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.mailSender = mailSender;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 发送验证码邮件到指定邮箱
     * 生成6位随机验证码并存储到session中，有效期5分钟
     *
     * @param email 目标邮箱地址
     * @param session HTTP会话，用于存储验证码信息
     * @return 发送是否成功
     */
    public boolean sendMimeMail(String email, HttpSession session) {
        try {
            log.info("发送验证码请求的Session ID: {}", session.getId());
            log.info("当前Session中的所有属性值: {}", Collections.list(session.getAttributeNames()));

            // 检查邮箱是否已注册
            List<User> existingUsers = userMapper.queryByEmail(email);
            if (existingUsers != null && !existingUsers.isEmpty()) {
                log.warn("邮箱已被注册: {}", email);
                return false;
            }

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setSubject("注册验证码 - 您的账户安全验证");

            String code = randomCode();

            // 存储到session，设置5分钟有效期
            session.setAttribute("email", email);
            session.setAttribute("code", code);
            session.setAttribute("codeTime", System.currentTimeMillis());

            log.info("设置Session属性: email={}, code={}", email, code);

            mailMessage.setText("尊敬的用户：\n\n" +
                    "您正在注册账户，验证码是：" + code + "\n" +
                    "有效期5分钟，请勿泄露给他人。\n\n" +
                    "如非本人操作，请忽略此邮件。");
            mailMessage.setTo(email);
            mailMessage.setFrom(from);

            mailSender.send(mailMessage);
            log.info("验证码邮件发送成功至: {}", email);
            return true;
        } catch (Exception e) {
            log.error("邮件发送失败: {}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * 生成6位随机数字验证码
     *
     * @return 6位数字验证码字符串
     */
    public String randomCode() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000));
    }

    /**
     * 验证验证码并完成用户注册
     * 检查session中的验证信息，验证通过后创建用户账户
     *
     * @param userVo 用户注册信息
     * @param session HTTP会话，包含验证信息
     * @return 注册是否成功
     */
    public boolean registered(UserVo userVo, HttpSession session) {
        try {
            log.info("注册请求的Session ID: {}", session.getId());
            log.info("当前Session中的所有属性: {}", Collections.list(session.getAttributeNames()));

            // 获取session中的验证信息
            String sessionEmail = (String) session.getAttribute("email");
            String sessionCode = (String) session.getAttribute("code");
            Long codeTime = (Long) session.getAttribute("codeTime");

            // 验证session信息完整性
            if (sessionEmail == null || sessionEmail.isEmpty() ||
                    sessionCode == null || sessionCode.isEmpty() ||
                    codeTime == null) {
                log.warn("Session验证信息不完整");
                return false;
            }

            // 验证验证码有效期（5分钟）
            long currentTime = System.currentTimeMillis();
            if (currentTime - codeTime > 5 * 60 * 1000) {
                log.warn("验证码已过期");
                session.removeAttribute("email");
                session.removeAttribute("code");
                session.removeAttribute("codeTime");
                return false;
            }

            // 验证邮箱和验证码匹配
            if (!sessionEmail.equals(userVo.getEmail())) {
                log.warn("Session邮箱与输入邮箱不匹配: {} != {}", sessionEmail, userVo.getEmail());
                return false;
            }

            if (!sessionCode.equals(userVo.getCode())) {
                log.warn("验证码不匹配");
                return false;
            }

            // 检查用户名是否已存在
            User existingUser = userMapper.selectByUsername(userVo.getUsername());
            if (existingUser != null) {
                log.warn("用户名已存在: {}", userVo.getUsername());
                return false;
            }

            // 数据转换并保存
            User user = UserVoToUser.toUser(userVo);
            // 加密密码
            user.setPassword(passwordEncoder.encode(userVo.getPassword()));
            // 设置默认角色
            user.setRole("ROLE_USER");
            user.setEnabled(1);

            log.info("准备插入用户: {}", user);

            int result = userMapper.insertUser(user);
            log.info("插入结果: {}", result);

            if (result > 0) {
                log.info("用户注册成功，ID: {}", user.getId());

                // 注册成功后清除session中的验证信息
                session.removeAttribute("email");
                session.removeAttribute("code");
                session.removeAttribute("codeTime");

                return true;
            } else {
                log.warn("用户插入失败");
                return false;
            }

        } catch (Exception e) {
            log.error("注册过程中发生异常: {}", e.getMessage(), e);
            return false;
        }
    }
}