package com.tao.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {

    @GetMapping("/hello")
    public String hello() {
        return "这是一个公开接口，无需登录即可访问";
    }

    @GetMapping("/info")
    public String info() {
        return "系统信息：Spring Boot 3 + 角色权限控制";
    }
}