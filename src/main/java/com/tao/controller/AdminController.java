package com.tao.controller;

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
    public String admin() {
        return "admin";
    }
}
