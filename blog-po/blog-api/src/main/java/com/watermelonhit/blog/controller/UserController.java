package com.watermelonhit.blog.controller;

import com.watermelonhit.blog.service.SysUserService;
import com.watermelonhit.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author watermelonhit
 * @DateTime 2021/8/27
 */
@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private SysUserService sysUserService;

    /**
     * 登录后返回用户信息
     * @param token
     * @return
     */
    @GetMapping("/currentUser")
    public Result getCurrentUser(@RequestHeader("Authorization") String token){
        return sysUserService.getCurrentUser(token);
    }
}
