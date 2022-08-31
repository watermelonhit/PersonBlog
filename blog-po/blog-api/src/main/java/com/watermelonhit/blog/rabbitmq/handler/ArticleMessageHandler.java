package com.watermelonhit.blog.rabbitmq.handler;

import com.watermelonhit.blog.enums.BlogRedisKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @Author watermelonhit
 * @DateTime 2022/8/24
 */
@Slf4j
@Component
public class ArticleMessageHandler {


    @Autowired
    StringRedisTemplate redisTemplate;

    public void deleteSingleArticleCache(Long articleId) {
        if (articleId == null) {
            return;
        }
        String params = DigestUtils.md5Hex(articleId.toString());
        this.redisTemplate.delete(BlogRedisKey.SINGLEARTICLE_REDISKEY.getKey() + params);
        //log.info("删除单篇文章缓存-redisKey:{}", BlogRedisKey.SINGLEARTICLE_REDISKEY.getKey() + params);
    }

    public void deleteArticleListCache() {
        Set<String> keys = this.redisTemplate.keys(BlogRedisKey.ListArticle_RedisKey.getKey());
        if (keys != null) {
            keys.forEach(s -> {
                this.redisTemplate.delete(s);
                //log.info("删除首页文章列表缓存-redisKey：{}", s);
            });
        }
    }

    public void deleteNewArticleListCache() {
        Boolean hasKey = this.redisTemplate.hasKey(BlogRedisKey.NEWARTICLE_REDISKEY.getKey());
        if (hasKey) {
            this.redisTemplate.delete(BlogRedisKey.NEWARTICLE_REDISKEY.getKey());
        }
        //log.info("删除首页最新文章列表缓存-redisKey：{}", BlogRedisKey.NEWARTICLE_REDISKEY.getKey());
    }

    public void deleteArchivesListCache() {
        Boolean hasKey = this.redisTemplate.hasKey(BlogRedisKey.ARCHIVES_REDISKEY.getKey());
        if (hasKey) {
            this.redisTemplate.delete(BlogRedisKey.ARCHIVES_REDISKEY.getKey());
        }
        //log.info("删除首页最新文章列表缓存-redisKey：{}", BlogRedisKey.ARCHIVES_REDISKEY.getKey());
    }


    public void deleteHostTagListCache() {
        Boolean hasKey = this.redisTemplate.hasKey(BlogRedisKey.HOTTAGS_REDISKEY.getKey());
        if (hasKey) {
            this.redisTemplate.delete(BlogRedisKey.HOTTAGS_REDISKEY.getKey());
        }
        //log.info("删除首页最新文章列表缓存-redisKey：{}", BlogRedisKey.HOTTAGS_REDISKEY.getKey());
    }

}
