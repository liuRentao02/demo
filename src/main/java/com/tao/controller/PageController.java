package com.tao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @ClassName PageController
 * @Description TODO
 * @Author LiuRentao
 * @Since 2025/8/27 17:26
 * @Version 1.0
 */
@Controller
public class PageController {

    @GetMapping("/page")
    public String home() {
        return "/page/home.html";
    }
}
