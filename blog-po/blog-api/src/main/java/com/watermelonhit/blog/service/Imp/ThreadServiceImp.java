package com.watermelonhit.blog.service.Imp;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.watermelonhit.blog.dao.Article;
import com.watermelonhit.blog.mapper.ArticleMapper;
import com.watermelonhit.blog.service.ThreadService;
import com.watermelonhit.blog.vo.ArticleVo;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @Author watermelonhit
 * @DateTime 2021/8/27
 */
@Service
public class ThreadServiceImp implements ThreadService {
    @Override
    @Async("taskExecutor")//使用了线程池
    public void updateArticleViewCount(ArticleMapper articleMapper, Article article) {
        int viewCount=article.getViewCounts();
        //新创建一个，减少对数据的破坏
        Article art = new Article();
        //不能为基本数据类型，基本数据类型会有默认值，不为null，mybatis-plus在更新时就会把非null值进行更新
        art.setViewCounts(viewCount+1);
        LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Article::getId,article.getId());
        //有技术点：防止在多线程下出现重复修改
        updateWrapper.eq(Article::getViewCounts,viewCount);
        articleMapper.update(art,updateWrapper);
    }
}
