package com.tao.controller;

import com.tao.entity.vo.UserVo;
import com.tao.service.Imp.MailService;
import com.tao.util.Result;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController - 用户注册控制器
 * 处理用户注册相关的请求，包括发送验证码和完成注册
 * 使用邮箱验证码机制确保注册安全性
 *
 * @author LiuRentao
 * @version 1.0
 * @since 2025/8/30 16:38
 */
@RestController
@RequiredArgsConstructor
public class UserController {

    private final MailService mailService;

    /**
     * 发送邮箱验证码
     * 接收邮箱地址，生成并发送6位数字验证码到指定邮箱
     * 验证码会存储在session中用于后续验证
     *
     * @param email 目标邮箱地址
     * @param httpSession HTTP会话对象，用于存储验证码信息
     * @return 发送结果
     */
    @PostMapping("/sendEmail")
    public Result<?> sendEmail(String email, HttpSession httpSession){
        boolean b = mailService.sendMimeMail(email, httpSession);
        if(b){
            return Result.success("验证码发送成功");
        }
        return Result.fail("验证码发送失败，请稍后重试");
    }

    /**
     * 用户注册接口
     * 接收用户注册信息和验证码，验证通过后完成用户注册
     * 验证码和邮箱信息需要与session中存储的信息匹配
     *
     * @param userVo 用户注册信息（包含用户名、密码、昵称、邮箱、验证码）
     * @param session HTTP会话对象，用于获取存储的验证信息
     * @return 注册结果
     */
    @PostMapping("/register")
    public Result<?> register(@RequestBody UserVo userVo, HttpSession session){
        boolean registered = mailService.registered(userVo, session);
        if(registered){
            return Result.success("注册成功");
        }
        return Result.fail("注册失败，请检查验证码或联系管理员");
    }
}