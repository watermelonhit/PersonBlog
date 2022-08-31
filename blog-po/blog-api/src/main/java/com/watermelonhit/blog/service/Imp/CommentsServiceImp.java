package com.watermelonhit.blog.service.Imp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.watermelonhit.blog.dao.Article;
import com.watermelonhit.blog.dao.Comment;
import com.watermelonhit.blog.dao.SysUser;
import com.watermelonhit.blog.mapper.ArticleMapper;
import com.watermelonhit.blog.mapper.CommentMapper;
import com.watermelonhit.blog.service.CommentsService;
import com.watermelonhit.blog.service.SysUserService;
import com.watermelonhit.blog.util.UserThreadLocal;
import com.watermelonhit.blog.vo.CommentVo;
import com.watermelonhit.blog.vo.Result;
import com.watermelonhit.blog.vo.UserVo;
import com.watermelonhit.blog.vo.params.CommentParams;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * @Author watermelonhit
 * @DateTime 2021/8/27
 */
@Service
public class CommentsServiceImp implements CommentsService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public Result getCommentsByArticleId(Long id) {
        //根据文章id获取评论列表
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getArticleId, id);
        queryWrapper.orderByAsc(Comment::getCreateDate);
        List<Comment> comments = this.commentMapper.selectList(queryWrapper);
        //将List<Comment> comments转变为List<CommentVo> comments
        List<CommentVo> commentVos = this.copyList(comments);
        return Result.success(commentVos);
    }

    @Override
    public Result comment(CommentParams commentParam) {
        SysUser sysUser = UserThreadLocal.get();
        Comment comment = new Comment();
        // 根据文章id获取文章，再把评论数加1
        Article article = this.articleMapper.selectById(commentParam.getArticleId());
        article.setCommentCounts(article.getCommentCounts() + 1);
        this.articleMapper.updateById(article);
        // 处理文章评论
        comment.setArticleId(commentParam.getArticleId());
        comment.setAuthorId(sysUser.getId());
        comment.setContent(commentParam.getContent());
        comment.setCreateDate(System.currentTimeMillis());
        Long parent = commentParam.getParent();
        if (parent == null || parent == 0) {
            comment.setLevel(1);
        } else {
            comment.setLevel(2);
        }
        comment.setParentId(parent == null ? 0 : parent);
        Long toUserId = commentParam.getToUserId();
        comment.setToUid(toUserId == null ? 0 : toUserId);
        //存入数据库
        this.commentMapper.insert(comment);

        CommentVo commentVo = this.copy(comment, new HashSet<Long>());
        return Result.success(commentVo);
    }

    private List<CommentVo> copyList(List<Comment> comments) {
        ArrayList<CommentVo> commentVos = new ArrayList<>();
        HashSet<Long> record = new HashSet<>();
        for (Comment comment : comments) {
            // 是子评论则跳过，已经被包含在其它评论里了
            if (record.contains(comment.getId())) {
                continue;
            }
            commentVos.add(this.copy(comment, record));
        }
        return commentVos;
    }

    @Autowired
    private SysUserService sysUserService;

    /**
     * 具有去重子评论
     *
     * @param comment
     * @param record
     * @return
     */
    private CommentVo copy(Comment comment, HashSet<Long> record) {
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment, commentVo);
        commentVo.setCreateDate(new Date(comment.getCreateDate()));
        commentVo.setId(String.valueOf(comment.getId()));
        //返回评论者信息
        UserVo userVo = this.sysUserService.getAuthorVoById(comment.getAuthorId());
        commentVo.setAuthor(userVo);
        Integer level = comment.getLevel();
        //查询子评论
        if (level == 1) {
            List<CommentVo> commentVos = this.getChildrenComments(comment.getId(), record);
            commentVo.setChildrens(commentVos);
        }
        //查询是写给谁的,二级评论才有
        if (level > 1) {
            UserVo toUser = this.sysUserService.getAuthorVoById(comment.getToUid());
            commentVo.setToUser(toUser);
        }
        return commentVo;
    }

    private List<CommentVo> getChildrenComments(Long id, HashSet<Long> record) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId, id);
        //确保是二级评论
        queryWrapper.eq(Comment::getLevel, 2);
        List<Comment> comments = this.commentMapper.selectList(queryWrapper);
        for (Comment c : comments) {
            record.add(c.getId());
        }
        //有点小饶，注意是限定了二级评论
        return this.copyList(comments);
    }

    @Override
    public void deleteComments(Long articleId) {
        // 删除相关评论
        LambdaQueryWrapper<Comment> query = new LambdaQueryWrapper<>();
        query.eq(Comment::getArticleId, articleId);
        this.commentMapper.delete(query);
    }
}
