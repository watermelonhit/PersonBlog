package com.watermelonhit.blog.vo.params;

import lombok.Data;

/**
 * @Author watermelonhit
 * @DateTime 2021/8/26
 */
@Data
public class LoginParams {
    private String account;
    private String password;
    private String nickname;
}
