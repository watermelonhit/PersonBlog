package com.watermelonhit.blog.rabbitmq;

import com.watermelonhit.blog.config.RabbitmqConfig;
import com.watermelonhit.blog.vo.message.ArticleMessage;
import com.watermelonhit.blog.vo.message.BoardMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 消息发送者，用来解决缓存一致性问题
 *
 * @Author watermelonhit
 * @DateTime 2022/8/21
 */
@Service
@Slf4j
public class BlogMessageSender {

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 由于发生了文章的修改，删除，发布，故需要更新文章缓存（单篇和首页文章列表）
     *
     * @param articleId
     */
    public void sendUpdateArticleMessage(Long articleId) {
        // 发送消息给消息队列，通知删除缓存
        ArticleMessage articleMessage = new ArticleMessage(articleId);
        String articleMessageJson = ArticleMessage.toJsonString(articleMessage);
        this.rabbitTemplate.convertAndSend(RabbitmqConfig.E_T, "blog.article.message", articleMessageJson);
        log.info("发送文章更新缓存消息！");
    }

    public void sendUpdateBoardMessage() {
        // 发送消息给消息队列，通知删除缓存
        BoardMessage boardMessage = new BoardMessage();
        String boardMessageJson = BoardMessage.toJsonString(boardMessage);
        this.rabbitTemplate.convertAndSend(RabbitmqConfig.E_T, "blog.board.message", boardMessageJson);
        log.info("发送留言板更新缓存消息！");
    }

}
