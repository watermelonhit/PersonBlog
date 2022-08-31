package com.watermelonhit.blog.controller;

import com.watermelonhit.blog.common.aop.LogAnnotation;
import com.watermelonhit.blog.service.LoginService;
import com.watermelonhit.blog.vo.Result;
import com.watermelonhit.blog.vo.params.LoginParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 修改用户昵称和密码
 *
 * @Author watermelonhit
 * @DateTime 2021/9/19
 */
@RestController
public class UpdateUserController {
    @Autowired
    private LoginService loginService;

    @LogAnnotation(module = "用户", operator = "用户修改昵称和密码")
    @PostMapping("/updateInfo")
    public Result updateInfo(@RequestBody LoginParams loginParams) {
        return this.loginService.updateUser(loginParams);
    }
}
