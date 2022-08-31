package com.watermelonhit.blog.service;

/** 扩展模块，为文章板块增添智能化
 * @Author watermelonhit
 * @DateTime 2022/7/3
 */
public interface ArticleAutoService {
    /**
     *
     * @param content 字符串（限3000字符数以内）字符串仅支持GBK编码，长度需小于3000字符数（即6000字节）
     * @param title 字符串（限200字符数）字符串仅支持GBK编码，长度需小于200字符数（即400字节），请输入前确认字符数没有超限，若字符数超长会返回错误。
     * @return
     */
    String getAutoSummary(String title,String content);
}
