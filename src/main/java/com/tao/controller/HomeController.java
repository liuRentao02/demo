package com.tao.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName HomeController
 * @Description TODO
 * @Author LiuRentao
 * @Since 2025/8/27 17:23
 * @Version 1.0
 */
@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }
}
