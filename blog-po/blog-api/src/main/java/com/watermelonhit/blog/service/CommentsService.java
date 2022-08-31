package com.watermelonhit.blog.service;

import com.watermelonhit.blog.vo.Result;
import com.watermelonhit.blog.vo.params.CommentParams;

/**
 * @Author watermelonhit
 * @DateTime 2021/8/27
 */
public interface CommentsService {
    Result getCommentsByArticleId(Long id);

    Result comment(CommentParams commentParams);

    void deleteComments(Long id);
}
