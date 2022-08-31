package com.watermelonhit.blog.dao;

import lombok.Data;

/**
 * @Author watermelonhit
 * @DateTime 2021/8/28
 */
@Data
public class ArticleTag {

    private Long id;

    private Long articleId;

    private Long tagId;
}
