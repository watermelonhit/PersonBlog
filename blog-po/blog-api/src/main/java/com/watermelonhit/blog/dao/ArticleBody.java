package com.watermelonhit.blog.dao;

import lombok.Data;

/**
 * @Author watermelonhit
 * @DateTime 2021/8/27
 */
@Data
public class ArticleBody {

    private Long id;
    private String content;
    private String contentHtml;
    private Long articleId;
}