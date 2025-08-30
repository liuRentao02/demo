package com.tao.controller;

import com.tao.util.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserController
 * @author LiuRentao
 * @since 2025/8/29 18:24
 * @version 1.0
 */
@RestController
@RequestMapping("/user")
public class MyUserController {

    @GetMapping("/test")
    public Result<?> test() {
        return Result.success("user");
    }
}
