package com.watermelonhit.blog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @Author watermelonhit
 * @DateTime 2021/8/27
 */
@Data
public class UserVo {

    private String nickname;

    private String avatar;
    //分布式id比较长，容易导致精度损失
    //转成json返回给前端，防止精度损失(二级评论)
    //@JsonSerialize(using = ToStringSerializer.class)
    private String id;
}