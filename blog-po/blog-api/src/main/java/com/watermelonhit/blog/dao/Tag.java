package com.watermelonhit.blog.dao;

import lombok.Data;

/**
 * @Author watermelonhit
 * @DateTime 2021/8/25
 */
@Data
public class Tag {

    private Long id;
    //头像
    private String avatar;

    private String tagName;

}