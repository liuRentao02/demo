package com.tao.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/hello")
    @PreAuthorize("hasAuthority('USER')")
    public String hello() {
        return "Hello, Security!";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String admin() {
        return "Admin Page";
    }
}