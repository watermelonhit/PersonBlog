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
 * @DateTime 2021/8/26
 */
@RestController
@RequestMapping("login")
public class LoginController {
    @Autowired
    private LoginService loginService;

    /**
     * 用户登录业务
     *
     * @param loginParams
     * @return
     */
    @LogAnnotation(module = "用户", operator = "用户进行了登录")
    @PostMapping()
    public Result loginUser(@RequestBody LoginParams loginParams) {
        return this.loginService.loginUser(loginParams);
    }
}
