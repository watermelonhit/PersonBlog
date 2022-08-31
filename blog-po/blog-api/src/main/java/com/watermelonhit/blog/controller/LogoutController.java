package com.watermelonhit.blog.controller;

import com.watermelonhit.blog.common.aop.LogAnnotation;
import com.watermelonhit.blog.service.LoginService;
import com.watermelonhit.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author watermelonhit
 * @DateTime 2021/8/27
 */
@RestController
@RequestMapping("logout")
public class LogoutController {
    @Autowired
    private LoginService loginService;

    @LogAnnotation(module = "用户", operator = "用户退出登录状态")
    @GetMapping
    public Result logout(@RequestHeader("Authorization") String token) {
        return this.loginService.logout(token);
    }
}
