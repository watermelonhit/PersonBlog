package com.watermelonhit.blog.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class Board {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Long authorId;
    private String context;
    private Short statue;
    private String ip;
    private Date createTime;
}
