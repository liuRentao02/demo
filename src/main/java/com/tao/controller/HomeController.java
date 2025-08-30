package com.tao.controller;

import com.tao.mapper.UserMapper;
import com.tao.util.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HomeController
 * @author LiuRentao
 * @since 2025/8/27 17:23
 * @version 1.0
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class HomeController {

    private final UserMapper userMapper;

    @GetMapping("/hello")
    public Result<?> hello() {
        return Result.success("hello");
    }

    @GetMapping("/user")
    public Result<?> user() {
        return Result.success(userMapper.selectList(null));
    }
}
