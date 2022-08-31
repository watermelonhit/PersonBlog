package com.watermelonhit.blog.vo.message;


import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author watermelonhit
 * @DateTime 2022/5/26
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ArticleMessage extends BlogMessage {

    private static final Gson gson = new Gson();
    private Long articleId;

    public static String toJsonString(ArticleMessage articleMessage) {
        return gson.toJson(articleMessage);
    }

    public static ArticleMessage fromJsonString(String jsonString) {
        return gson.fromJson(jsonString, ArticleMessage.class);
    }

}
