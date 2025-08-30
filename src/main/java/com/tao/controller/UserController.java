package com.tao.controller;

import com.tao.entity.vo.UserVo;
import com.tao.service.Imp.MailService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * UserController
 *
 * @author LiuRentao
 * @version 1.0
 * @since 2025/8/30 16:38
 */
@Controller
@RequiredArgsConstructor
public class UserController {

    private final MailService mailService;

    @PostMapping("/sendEmail")
    @ResponseBody
    public String sendEmail(String email, HttpSession httpSession){
        boolean b = mailService.sendMimeMail(email, httpSession);
        if(b){
            return "success";
        }
        return "false";
    }

    @PostMapping("/register")
    @ResponseBody
    public String register(UserVo userVo, HttpSession session){
        boolean registered = mailService.registered(userVo, session);
        if(registered){
            return "success";
        }
        return "false";
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(String email, String password){
        boolean b = mailService.loginIn(email, password);
        if(b){
            return "success";
        }
        return "false";
    }
}
