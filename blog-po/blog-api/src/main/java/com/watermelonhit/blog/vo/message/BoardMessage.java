package com.watermelonhit.blog.vo.message;

import com.google.gson.Gson;

/**
 * @Author watermelonhit
 * @DateTime 2022/8/24
 */
public class BoardMessage extends BlogMessage {

    private static final Gson gson = new Gson();

    public static String toJsonString(BoardMessage boardMessage) {
        return gson.toJson(boardMessage);
    }

    public static BoardMessage fromJsonString(String jsonString) {
        return gson.fromJson(jsonString, BoardMessage.class);
    }
}


