package com.watermelonhit.blog.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author watermelonhit
 * @DateTime 2021/8/25
 */
@Data
public class ArticleVo {

    private String id;

    private String title;

    private String summary;

    private Integer commentCounts;

    private Integer viewCounts;

    private Integer weight;
    /**
     * 创建时间
     */
    private String createDate;

    private String author;

    private String account;

    private ArticleBodyVo body;

    private List<TagVo> tags;

    private CategoryVo category;

}
