package com.watermelonhit.blog.vo.params;

import lombok.Data;

/**
 * @Author watermelonhit
 * @DateTime 2021/8/27
 */
@Data
public class CommentParams {
    private Long articleId;//文章id
    private String content;//评论内容
    private Long parent;//父评论id
    private Long toUserId;//被评论的用户id

}
