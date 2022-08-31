package com.watermelonhit.blog.vo;

import com.alibaba.fastjson.JSONObject;
import com.watermelonhit.blog.vo.message.ArticleMessage;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author watermelonhit
 * @DateTime 2022/5/26
 */
@Data
public class MyMessage<T> implements Serializable {
    public T message;
    // 指明消息是哪种类型
    public String type;

    public MyMessage(){}

    public MyMessage(T message,String type){
        this.message=message;
        this.type=type;
    }

    public ArticleMessage getArticleMessage(){
        String s = JSONObject.toJSONString(this.message);
        return JSONObject.parseObject(s,ArticleMessage.class);
    }

}
