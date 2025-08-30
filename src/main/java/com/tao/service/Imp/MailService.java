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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpSession;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
@Transactional
public class MailService {

    private final JavaMailSender mailSender;
    private final UserMapper userMapper;

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    public MailService(JavaMailSender mailSender, UserMapper userMapper) {
        this.mailSender = mailSender;
        this.userMapper = userMapper;
    }

    /**
     * 给前端输入的邮箱发送验证码
     * @param email 目标邮箱地址
     * @param session HTTP会话
     * @return 发送是否成功
     */
    public boolean sendMimeMail(String email, HttpSession session) {
        try {
            log.info("发送验证码请求的Session ID: {}", session.getId());
            log.info("当前Session中的所有属性值: {}", Collections.list(session.getAttributeNames()));

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setSubject("验证码邮件");

            String code = randomCode();

            // 存储到session
            session.setAttribute("email", email);
            session.setAttribute("code", code);
            session.setAttribute("codeTime", System.currentTimeMillis());

            log.info("设置Session属性: email={}, code={}", email, code);

            mailMessage.setText("您收到的验证码是：" + code + "，有效期为5分钟");
            mailMessage.setTo(email);
            mailMessage.setFrom(from);

            mailSender.send(mailMessage);
            return true;
        } catch (Exception e) {
            log.error("邮件发送失败: {}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * 随机生成6位数的验证码
     * @return 6位数字验证码
     */
    public String randomCode() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000));
    }

    /**
     * 检验验证码是否一致并完成注册
     * @param userVo 用户注册信息
     * @param session HTTP会话
     * @return 注册是否成功
     */
    public boolean registered(UserVo userVo, HttpSession session) {
        try {
            log.info("注册请求的Session ID: {}", session.getId());
            log.info("当前Session中的所有属性: {}", Collections.list(session.getAttributeNames()));

            // 获取session中的验证信息
            String email = (String) session.getAttribute("email");
            String code = (String) session.getAttribute("code");

            log.info("Session中的email: {}, 用户输入的email: {}", email, userVo.getEmail());
            log.info("Session中的code: {}, 用户输入的code: {}", code, userVo.getCode());

            // 验证session中的信息
            if (email == null || email.isEmpty()) {
                log.warn("Session中email为空");
                return false;
            }

            if (!email.equals(userVo.getEmail())) {
                log.warn("Session邮箱与输入邮箱不匹配: {} != {}", email, userVo.getEmail());
                return false;
            }

            if (code == null || !code.equals(userVo.getCode())) {
                log.warn("验证码不匹配或为空");
                return false;
            }

            // 数据转换并保存
            User user = UserVoToUser.toUser(userVo);
            log.info("准备插入用户: {}", user);

            int result = userMapper.insertUser(user);
            log.info("插入结果: {}", result);

            if (result > 0) {
                log.info("用户插入成功，ID: {}", user.getId()); // 如果id是自增的
                // 立即查询验证数据是否真的插入
                List<User> insertedUser = userMapper.queryByEmail(user.getEmail());
                log.info("插入后查询结果: {}", insertedUser);
            } else {
                log.warn("用户插入失败");
            }

            // 注册成功后清除session中的验证信息
            session.removeAttribute("email");
            session.removeAttribute("code");

            return result > 0;
        } catch (Exception e) {
            log.error("注册过程中发生异常: {}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * 用户登录验证
     * @param email 邮箱
     * @param password 密码
     * @return 登录是否成功
     */
    public boolean loginIn(String email, String password) {
        List<User> users = userMapper.queryByEmail(email);
        AtomicBoolean flag = new AtomicBoolean(false);
        if (users == null || users.isEmpty()) {
            return flag.get();
        }else {
            users.forEach(u->{
                if (u.getPassword().equals(password)) {
                    flag.set(true);
                }
            });
        }

        return flag.get();
    }
}