package com.watermelonhit.blog.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author watermelonhit
 * @DateTime 2022/8/18
 */
@Configuration
public class RabbitmqConfig {

    // topic模式
    public static final String Q_T_1 = "BLOG_ARTICLE_MESSAGE_QUEUE";
    public static final String Q_T_2 = "BLOG_BOARD_MESSAGE_QUEUE";
    public static final String E_T = "BLOG_Exechange";


    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(E_T);
    }

    @Bean
    public Queue queue1() {
        return new Queue(Q_T_1);
    }

    @Bean
    public Queue queue2() {
        return new Queue(Q_T_2);
    }

    @Bean
    public Binding bind1() {
        return BindingBuilder.bind(this.queue1()).to(this.topicExchange()).with("blog.article.#");
    }

    @Bean
    public Binding bind2() {
        return BindingBuilder.bind(this.queue2()).to(this.topicExchange()).with("blog.board.#");
    }

}
