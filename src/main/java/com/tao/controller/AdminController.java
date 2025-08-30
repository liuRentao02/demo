package com.tao.controller;

import com.tao.util.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AdminController
 * @author LiuRentao
 * @since 2025/8/29 16:19
 * @version 1.0
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/test")
    public Result<?> admin() {
        return Result.success("admin");
    }
}
