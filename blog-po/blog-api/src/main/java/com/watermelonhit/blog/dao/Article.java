package com.watermelonhit.blog.dao;

import lombok.Data;

/**
 * @Author watermelonhit
 * @DateTime 2021/8/25
 */
@Data
public class Article {

    public static final int Article_TOP = 1;

    public static final int Article_Common = 0;

    private Long id;

    private String title;

    private String summary;
    //不能为基本数据类型，基本数据类型会有默认值，不为null，mybatis-plus在更新时就会把非null值进行更新
    private Integer commentCounts;

    private Integer viewCounts;

    /**
     * 作者id
     */
    private Long authorId;
    /**
     * 内容id
     */
    private Long bodyId;
    /**
     *类别id
     */
    private Long categoryId;

    /**
     * 置顶
     */
    private Integer weight;


    /**
     * 创建时间
     */
    private Long createDate;
}
