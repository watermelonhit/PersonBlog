package com.watermelonhit.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author watermelonhit
 * @DateTime 2022/8/24
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoardVo {
    private String authorName;
    private String context;
    private Date createTime;
}
