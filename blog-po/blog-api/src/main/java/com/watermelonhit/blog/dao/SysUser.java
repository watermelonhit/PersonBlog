package com.watermelonhit.blog.dao;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @Author watermelonhit
 * @DateTime 2021/8/25
 */
@Data
public class SysUser {

    private Long id;

    private String account;

    private Integer admin;

    private String avatar;

    private Long createDate;

    private Integer deleted;

    private String email;

    private Long lastLogin;

    private String mobilePhoneNumber;

    private String nickname;

    private String password;

    private String salt;

    private String status;
}