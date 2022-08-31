package com.watermelonhit.blog.controller;

import com.watermelonhit.blog.common.aop.LogAnnotation;
import com.watermelonhit.blog.service.LoginService;
import com.watermelonhit.blog.vo.Result;
import com.watermelonhit.blog.vo.params.LoginParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author watermelonhit
 * @DateTime 2021/8/27
 */
@RestController
@RequestMapping("register")
public class RegisterController {
    @Autowired
    private LoginService loginService;

    @LogAnnotation(module = "用户", operator = "用户进行注册账号")
    @PostMapping
    public Result register(@RequestBody LoginParams loginParams) {
        return this.loginService.registerUser(loginParams);
    }
}
