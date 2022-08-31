package com.watermelonhit.blog.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author watermelonhit
 * @DateTime 2022/8/21
 */
@Getter
@AllArgsConstructor
public enum BlogRedisKey {
    // 文章板块
    NEWARTICLE_REDISKEY("首页最新文章::ArticleController::getNewArticles::"),
    SINGLEARTICLE_REDISKEY("获取单篇文章::ArticleController::getArticleById::"),
    ARCHIVES_REDISKEY("首页归档::ArticleController::listArchives::"),
    ListArticle_RedisKey("首页文章列表显示::*"),
    HOTTAGS_REDISKEY("首页最热标签::TagController::getHotTags::"),

    // 首页轮播图
    BLOG_POSTERS("BLOG_POSTERS"),

    // 留言板块
    BOARD_REDISKEY("留言板获取所有留言::BoardController::listBoards::");

    private final String key;
}
