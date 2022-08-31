package com.watermelonhit.blog.service.Imp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.watermelonhit.blog.dao.*;
import com.watermelonhit.blog.mapper.*;
import com.watermelonhit.blog.rabbitmq.BlogMessageSender;
import com.watermelonhit.blog.service.*;
import com.watermelonhit.blog.util.UserThreadLocal;
import com.watermelonhit.blog.vo.ArticleBodyVo;
import com.watermelonhit.blog.vo.ArticleVo;
import com.watermelonhit.blog.vo.Result;
import com.watermelonhit.blog.vo.TagVo;
import com.watermelonhit.blog.vo.params.ArticleParam;
import com.watermelonhit.blog.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * @Author watermelonhit
 * @DateTime 2021/8/25
 */
@Service
public class ArticleServiceImp implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private TagService tagServiceImp;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private ArchiveMapper archiveMapper;
    @Autowired
    private ArticleBodyMapper articleBodyMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ArticleTagMapper articleTagMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private CommentsService commentsService;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private ThreadService threadService;

    @Resource
    private BlogMessageSender blogMessageSender;

    /**
     * 首页展示文章（带有tag，author)
     *
     * @param pageParams
     * @return
     */
    @Override
    public Result listArticles(PageParams pageParams) {
        Page<Article> articlePage = new Page<Article>(pageParams.getPage(), pageParams.getPageSize());

        // 判断是根据创建日期升序(+)还是降序(-)
        String sort = pageParams.getSort();
        if (sort == null || "-".equals(sort) || "desc".equals(sort)) {
            sort = "desc";
        } else {
            sort = "asc";
        }

        IPage<Article> iPage = this.articleMapper.listArticle(
                articlePage,
                pageParams.getCategoryId(),
                pageParams.getTagId(),
                pageParams.getYear(),
                pageParams.getMonth(),
                sort
        );

        // msg返回查询到的条数
        Result success = Result.success(this.copyList(iPage.getRecords(), true, true));
        success.setMsg("" + this.getArticleTotal());
        return success;
    }

    /**
     * 返回最热文章（不带author，tag)
     *
     * @param limit
     * @return
     */
    @Override
    public Result getHotArticles(int limit) {
        LambdaQueryWrapper<Article> articleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        articleLambdaQueryWrapper.orderByDesc(Article::getViewCounts);
        articleLambdaQueryWrapper.select(Article::getId, Article::getTitle);
        articleLambdaQueryWrapper.last("limit " + limit);
        List<Article> articleList = this.articleMapper.selectList(articleLambdaQueryWrapper);
        return Result.success(this.copyList(articleList, false, false));
    }

    @Override
    public Result getNewArticles(int limit) {
        LambdaQueryWrapper<Article> articleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        articleLambdaQueryWrapper.orderByDesc(Article::getCreateDate);
        articleLambdaQueryWrapper.select(Article::getId, Article::getTitle);
        articleLambdaQueryWrapper.last("limit " + limit);
        List<Article> articleList = this.articleMapper.selectList(articleLambdaQueryWrapper);
        return Result.success(this.copyList(articleList, false, false));
    }

    @Override
    public Result listArchives() {
        return Result.success(this.archiveMapper.listArchives());
    }


    /**
     * 首页点击文章详情后，获取文章内容和分类
     *
     * @param id
     * @return
     */
    @Override
    public Result getArticleById(Long id) {
        Article article = this.articleMapper.selectById(id);
        if (article == null) {
            return Result.success("暂无该记录！");
        } else {
            ArticleVo articleVo = this.copy(article, true, true, true, true);
            //阅读完成后（而不是立马加1，所以传入article），异步处理阅读数加一（开启多线程）
            this.threadService.updateArticleViewCount(this.articleMapper, article);
            return Result.success(articleVo);
        }
    }

    /**
     * 发布文章(涉及修改)
     * 更新：加入了动态标签
     *
     * @param articleParam
     * @return
     */
    @Override
    public Result publish(ArticleParam articleParam) {

        // 获取评论和阅读数
        int viewCounts = 0;
        int commentCounts = 0;
        Long articleId = articleParam.getId();

        // 判断是否是更新操作
        boolean isEdit = (articleId != null);

        // 若是更新操作则要删除原有的东西
        if (isEdit) {
            Article article = this.articleMapper.selectById(articleId);
            viewCounts = article.getViewCounts();
            commentCounts = article.getCommentCounts();
            this.deleteArticleById(articleId, false);
        }

        // 为文章绑定属性
        SysUser sysUser = UserThreadLocal.get();
        Article article = new Article();
        article.setAuthorId(sysUser.getId());
        article.setCreateDate(System.currentTimeMillis());
        article.setCategoryId(Long.parseLong(articleParam.getCategory().getId()));
        article.setSummary(articleParam.getSummary());
        article.setWeight(Article.Article_Common);
        article.setViewCounts(viewCounts);
        article.setCommentCounts(commentCounts);
        article.setTitle(articleParam.getTitle());

        // 插入数据库后，主键自增，会返回一个值到对象中
        this.articleMapper.insert(article);
        Long newArticleId = article.getId();

        // 保存新tag,并加入tags中
        this.saveTags(articleParam, newArticleId);

        // 重新绑定上评论
        if (commentCounts != 0) {
            LambdaUpdateWrapper<Comment> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Comment::getArticleId, articleId);
            updateWrapper.set(Comment::getArticleId, newArticleId);
            this.commentMapper.update(null, updateWrapper);
        }

        //把articleBody存入数据库中
        ArticleBody articleBody = new ArticleBody();
        articleBody.setArticleId(article.getId());
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHtml());
        this.articleBodyMapper.insert(articleBody);

        //更新article
        article.setBodyId(articleBody.getId());
        this.articleMapper.updateById(article);

        // 从并发角度，采用先更新数据库后再删除缓存
        this.blogMessageSender.sendUpdateArticleMessage(articleId == null ? newArticleId : articleId);

        // 返回更新结果
        HashMap<String, String> map = new HashMap<>();
        map.put("id", article.getId().toString());
        return Result.success(map);
    }


    @Override
    public Result deleteArticleById(Long id, boolean deleteComments) {
        // 删除关联Tags，但是Tag仍保留
        this.tagMapper.deleteTagsByArticleId(id);
        Article article = this.articleMapper.selectById(id);
        Long bodyId = article.getBodyId();
        // 删除文章内容
        this.articleBodyMapper.deleteById(bodyId);
        // 删除相关评论
        if (deleteComments) {
            this.commentsService.deleteComments(id);
        }
        // 删除文章
        this.articleMapper.deleteById(id);

        // 删除缓存 (注意修改文章会发生两次删除缓存，但是由于相关操作具有幂等性，所以无碍)
        this.blogMessageSender.sendUpdateArticleMessage(id);

        return Result.success("success");
    }

    @Override
    public void saveTags(ArticleParam articleParam, Long articleId) {
        List<TagVo> tags = articleParam.getTags();
        List<String> dynamicTags = articleParam.getDynamicTags();

        // 从数据库中获取所有的tags,对动态tag进行去重后保存到数据库中
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Tag::getTagName);
        List<Tag> tagList = this.tagMapper.selectList(queryWrapper);
        HashSet<String> tagNameSet = new HashSet<>();
        for (Tag tag : tagList) {
            tagNameSet.add(tag.getTagName());
        }
        for (String dynamicTag : dynamicTags) {
            // 如果动态标签是新创建的
            if (!tagNameSet.contains(dynamicTag)) {
                // 向数据库插入新的tag
                Tag tag = new Tag();
                tag.setTagName(dynamicTag);
                tag.setAvatar("/static/tag/yu.png");
                this.tagMapper.insert(tag);
                //统一，将tag转为tagVo
                TagVo tagVo = new TagVo();
                if (tag.getId() != null) {
                    tagVo.setId(String.valueOf(tag.getId()));
                }
                tags.add(tagVo);
            }
        }

        //遍历获取所有标签,并且插入ArticleTag表中
        if (tags != null) {
            for (TagVo tag : tags) {
                ArticleTag articletag = new ArticleTag();
                articletag.setArticleId(articleId);
                articletag.setTagId(Long.parseLong(tag.getId()));
                this.articleTagMapper.insert(articletag);
            }
        }
    }

    @Override
    public Result searchArticle(String search) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId, Article::getTitle);
        queryWrapper.like(Article::getTitle, search);

        List<Article> articles = this.articleMapper.selectList(queryWrapper);
        return Result.success(this.copyList(articles, false, false));

    }

    /**
     * 返回文章总数
     *
     * @return
     */
    @Override
    public String getArticleTotal() {
        return (this.articleMapper.getTotal()) + "";
    }

    @Autowired
    ArticleAutoService articleAutoService;

    @Override
    public Result getAutoSummary(ArticleParam articleParam) {
        String title = articleParam.getTitle();
        String content = articleParam.getBody().getContent();
        if (title.length() <= 200 && content.length() <= 3000) {
            String autoSummary = this.articleAutoService.getAutoSummary(title, content);
            return Result.success(autoSummary);
        }
        return Result.fail(505, "标题或文章内容过长！");
    }

    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor) {
        List<ArticleVo> articleVos = new ArrayList<>();
        for (Article record : records) {
            articleVos.add(this.copy(record, isTag, isAuthor, false, false));
        }
        return articleVos;
    }

    private List<ArticleVo> copyList(List<Article> records, boolean isTag, boolean isAuthor, boolean isArticleBody, boolean isCategory) {
        List<ArticleVo> articleVos = new ArrayList<>();
        for (Article record : records) {
            articleVos.add(this.copy(record, isTag, isAuthor, isArticleBody, isCategory));
        }
        return articleVos;
    }

    private ArticleVo copy(Article article, boolean isTag, boolean isAuthor, boolean isArticleBody, boolean isCategory) {
        ArticleVo articleVo = new ArticleVo();
        //复制相同的属性
        BeanUtils.copyProperties(article, articleVo);
        articleVo.setId(String.valueOf(article.getId()));
        if (isTag) {
            Long id = article.getId();
            articleVo.setTags(this.tagServiceImp.getTagVoListByArticleId(id));
        }
        if (isAuthor) {
            Long authorId = article.getAuthorId();
            articleVo.setAuthor(this.sysUserService.getSysUserById(authorId).getNickname());
            articleVo.setAccount(this.sysUserService.getSysUserById(authorId).getAccount());
        }
        if (isArticleBody) {
            articleVo.setBody(this.getArticleBodyVoById(article.getBodyId()));
        }
        if (isCategory) {
            Long id = article.getCategoryId();
            articleVo.setCategory(this.categoryService.getCategoryById(id));
        }
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        return articleVo;
    }

    private ArticleBodyVo getArticleBodyVoById(long id) {
        ArticleBody articleBody = this.articleBodyMapper.selectById(id);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }
}
