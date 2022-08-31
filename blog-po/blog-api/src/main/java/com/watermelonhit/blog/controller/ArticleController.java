package com.watermelonhit.blog.controller;

import com.watermelonhit.blog.common.aop.Cache;
import com.watermelonhit.blog.common.aop.LogAnnotation;
import com.watermelonhit.blog.service.ArticleService;
import com.watermelonhit.blog.vo.Result;
import com.watermelonhit.blog.vo.params.ArticleParam;
import com.watermelonhit.blog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @Author watermelonhit
 * @DateTime 2021/8/25
 */
@RestController
@RequestMapping("articles")
public class ArticleController {
    @Autowired
    private ArticleService articleServiceImp;

    /**
     * 首页文章列表(带有tag，author)
     *
     * @param pageParams
     * @return
     */
    @Cache(expire = 24 * 60 * 1000, name = "首页文章列表显示")
    @PostMapping
    public Result listArticles(@RequestBody PageParams pageParams) {
        return this.articleServiceImp.listArticles(pageParams);
    }

    /**
     * 文章查询
     *
     * @param articleParam
     * @return
     */
    @LogAnnotation(module = "文章模块", operator = "用户进行了文章查询")
    @PostMapping("search")
    public Result search(@RequestBody ArticleParam articleParam) {
        String search = articleParam.getSearch();
        return this.articleServiceImp.searchArticle(search);
    }

    /**
     * 返回最热文章（不带author，tag)
     *
     * @return
     */
    @PostMapping("/hot")
    public Result getHotArticles() {
        int limit = 5;
        return this.articleServiceImp.getHotArticles(limit);
    }

    /**
     * 首页 最新文章
     *
     * @return
     */
    @Cache(expire = 60 * 60 * 1000, name = "首页最新文章")
    @PostMapping("/new")
    public Result getNewArticles() {
        int limit = 5;
        return this.articleServiceImp.getNewArticles(limit);
    }

    /**
     * 首页归档
     *
     * @return
     */
    @Cache(expire = 60 * 60 * 1000, name = "首页归档")
    @PostMapping("/listArchives")
    public Result listArchives() {
        return this.articleServiceImp.listArchives();
    }

    /**
     * 编辑修改文章时，返回指定文章内容(需要解决缓存一致性问题，已解决)
     *
     * @param id
     * @return
     */
    @LogAnnotation(module = "文章", operator = "用户进行修改文章")
    @PostMapping("/{id}")
    public Result getArticleByIdToEdit(@PathVariable("id") Long id) {
        return this.getArticleById(id);
    }

    /**
     * 文章详情
     *
     * @param id
     * @return
     */
    @PostMapping("/view/{id}")
//    @Cache(expire = 24 * 60 * 60 * 1000, name = "获取单篇文章")
    public Result getArticleById(@PathVariable("id") Long id) {
        return this.articleServiceImp.getArticleById(id);
    }

    /**
     * 通过Id删除文章(需要解决缓存一致性问题，已解决)
     */
    @Transactional
    @LogAnnotation(module = "文章", operator = "用户进行删除文章")
    @PostMapping("/delete/{id}")
    public Result deleteArticleById(@PathVariable("id") Long id) {
        return this.articleServiceImp.deleteArticleById(id, true);
    }

    /**
     * 发布文章(需要解决缓存一致性问题，已解决)
     */
    //利用aop进行打印日志
    @Transactional
    @LogAnnotation(module = "文章", operator = "用户发表文章")
    @PostMapping("/publish")
    public Result publish(@RequestBody ArticleParam articleParam) {
        return this.articleServiceImp.publish(articleParam);
    }

    @LogAnnotation(module = "文章", operator = "用户采用了自动生成文章摘要")
    @PostMapping("/autoSummary")
    public Result getAutoSummary(@RequestBody ArticleParam articleParam) {
        return this.articleServiceImp.getAutoSummary(articleParam);
    }

}
