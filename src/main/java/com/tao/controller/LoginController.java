package com.tao.controller;

import com.tao.entity.vo.Login;
import com.tao.util.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * LoginController
 *
 * @author LiuRentao
 * @version 1.0
 * @since 2025/8/28 15:19
 */
@RestController
@RequestMapping("/api")
public class LoginController {

    @PostMapping("/login")
    public Result<Object> login(@RequestBody Login user) {
        System.out.println(user.toString());
        return Result.success("登录成功");
    }
}
