package com.watermelonhit.blog.service;

import com.watermelonhit.blog.dao.SysUser;
import com.watermelonhit.blog.vo.Result;
import com.watermelonhit.blog.vo.UserVo;

/**
 * @Author watermelonhit
 * @DateTime 2021/8/26
 */
public interface SysUserService {
     SysUser getSysUserById(long id);

    SysUser findUser(String account, String password);

    Result getCurrentUser(String token);

    SysUser findUserByAccount(String account);

    void save(SysUser sysUser);

    UserVo getAuthorVoById(Long authorId);


    void updateUserById(SysUser user);
}
