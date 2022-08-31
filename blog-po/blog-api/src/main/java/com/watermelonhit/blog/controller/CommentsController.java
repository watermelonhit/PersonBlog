package com.watermelonhit.blog.controller;

import com.watermelonhit.blog.common.aop.LogAnnotation;
import com.watermelonhit.blog.service.CommentsService;
import com.watermelonhit.blog.vo.Result;
import com.watermelonhit.blog.vo.params.CommentParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author watermelonhit
 * @DateTime 2021/8/27
 */
@RestController
@RequestMapping("comments")
public class CommentsController {
    @Autowired
    private CommentsService commentsService;

    /**
     * 显示评论
     * @param id
     * @return
     */
    @GetMapping("/article/{id}")
    public Result getCommentsByArticleId(@PathVariable("id")Long id){
        return commentsService.getCommentsByArticleId(id);
    }
    /**
     * 评论功能
     */
    @LogAnnotation(module = "评论",operator = "用户发表评论")
    @PostMapping("/create/change")
    public Result comment(@RequestBody CommentParams commentParams){
        return commentsService.comment(commentParams);
    }
}
