package com.watermelonhit.blog.service;

import com.watermelonhit.blog.dao.Article;
import com.watermelonhit.blog.mapper.ArticleMapper;
import com.watermelonhit.blog.vo.ArticleVo;

/**
 * @Author watermelonhit
 * @DateTime 2021/8/27
 */
public interface ThreadService {
    void updateArticleViewCount(ArticleMapper articleMapper, Article article);
}
