package com.watermelonhit.blog.rabbitmq;

import com.watermelonhit.blog.config.RabbitmqConfig;
import com.watermelonhit.blog.enums.BlogRedisKey;
import com.watermelonhit.blog.rabbitmq.handler.ArticleMessageHandler;
import com.watermelonhit.blog.service.ArticleService;
import com.watermelonhit.blog.vo.message.ArticleMessage;
import com.watermelonhit.blog.vo.message.BoardMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author watermelonhit
 * @DateTime 2022/8/22
 */
@Slf4j
@Component // 别忘了
public class BlogMessageConsumer {

    @Autowired
    ArticleService articleService;
    @Autowired
    ArticleMessageHandler articleMessageHandler;

    @Autowired
    StringRedisTemplate redisTemplate;

    /**
     * 处理文章相关的缓存
     *
     * @param message
     */
    @RabbitListener(queues = RabbitmqConfig.Q_T_1)
    public void handlerArticleMessage(String message) {
        log.info("收到更新文章缓存消息");
        if (message == null) {
            return;
        }
        ArticleMessage articleMessage = ArticleMessage.fromJsonString(message);
        Long articleId = articleMessage.getArticleId();
        if (articleId == null) {
            return;
        }
        // 更新相应的缓存数据
        this.articleMessageHandler.deleteArticleListCache();
        this.articleMessageHandler.deleteSingleArticleCache(articleId);
        this.articleMessageHandler.deleteNewArticleListCache();
        this.articleMessageHandler.deleteArchivesListCache();
        this.articleMessageHandler.deleteHostTagListCache();
    }

    /**
     * 处理留言板相关的缓存
     *
     * @param message
     */
    @RabbitListener(queues = RabbitmqConfig.Q_T_2)
    public void handlerBoardMessage(String message) {
        if (message == null) {
            return;
        }
        BoardMessage boardMessage = BoardMessage.fromJsonString(message);
        if (boardMessage == null) {
            return;
        }
        log.info("收到更新留言板缓存消息");
        // 更新相应的缓存数据
        this.redisTemplate.delete(BlogRedisKey.BOARD_REDISKEY.getKey());
    }


}
