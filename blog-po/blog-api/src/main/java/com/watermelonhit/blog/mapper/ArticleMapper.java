package com.watermelonhit.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.watermelonhit.blog.dao.Article;


/**
 * @Author watermelonhit
 * @DateTime 2021/8/25
 */

public interface ArticleMapper extends BaseMapper<Article> {
    IPage<Article> listArticle(Page<Article> articlePage, Long categoryId, Long tagId, String year, String month,String sort);
    Integer getTotal();
}
