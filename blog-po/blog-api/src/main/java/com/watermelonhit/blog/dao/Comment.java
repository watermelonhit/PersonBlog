package com.watermelonhit.blog.dao;

import lombok.Data;

/**
 * @Author watermelonhit
 * @DateTime 2021/8/27
 */
@Data
public class Comment {
    
    private Long id;

    private String content;

    private Long createDate;

    private Long articleId;

    private Long authorId;

    private Long parentId;

    private Long toUid;

    private Integer level;

}
