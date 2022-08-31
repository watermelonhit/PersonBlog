package com.watermelonhit.blog.service;

import com.watermelonhit.blog.dao.SysUser;
import com.watermelonhit.blog.vo.Result;
import com.watermelonhit.blog.vo.params.LoginParams;

/**
 * @Author watermelonhit
 * @DateTime 2021/8/26
 */
public interface LoginService {
     Result loginUser(LoginParams loginParams);

    SysUser checkToken(String token);

    Result logout(String token);

    Result registerUser(LoginParams loginParams);

    Result updateUser(LoginParams loginParams);
}
