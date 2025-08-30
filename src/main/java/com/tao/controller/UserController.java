package com.tao.controller;

import com.tao.entity.vo.LoginUser;
import com.tao.entity.vo.UserVo;
import com.tao.service.Imp.MailService;
import com.tao.util.Result;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController
 *
 * @author LiuRentao
 * @version 1.0
 * @since 2025/8/30 16:38
 */
@RestController
@RequiredArgsConstructor
public class UserController {

    private final MailService mailService;

    @PostMapping("/sendEmail")
    public Result<?> sendEmail(String email, HttpSession httpSession){
        boolean b = mailService.sendMimeMail(email, httpSession);
        if(b){
            return Result.success("success");
        }
        return Result.fail("false");
    }

    @PostMapping("/register")
    public Result<?> register(@RequestBody UserVo userVo, HttpSession session){
        boolean registered = mailService.registered(userVo, session);
        if(registered){
            return Result.success("success");
        }
        return Result.fail("false");
    }

    @PostMapping("/login")
    public Result<?> login(@RequestBody LoginUser user){
        boolean b = mailService.loginIn(user.getEmail(), user.getPassword());
        if(b){
            return Result.success("success");
        }
        return Result.fail("false");
    }
}
