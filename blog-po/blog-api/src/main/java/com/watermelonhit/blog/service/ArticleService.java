package com.watermelonhit.blog.service;

import com.watermelonhit.blog.vo.Result;

import com.watermelonhit.blog.vo.params.ArticleParam;
import com.watermelonhit.blog.vo.params.PageParams;


/**
 * @Author watermelonhit
 * @DateTime 2021/8/25
 */

public interface ArticleService {

    Result listArticles(PageParams pageParams);

    Result getHotArticles(int limit);

    Result getNewArticles(int limit);

    Result listArchives();

    Result getArticleById(Long id);

    Result publish(ArticleParam articleParam);

    Result deleteArticleById(Long id, boolean deleteComments);

    void saveTags(ArticleParam articleParam,Long articleId);

    Result searchArticle(String search);

    String getArticleTotal();

    Result getAutoSummary(ArticleParam articleParam);
}
