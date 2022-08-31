package com.watermelonhit.blog.dao;

/** 记录一些缓存时间
 * @Author watermelonhit
 * @DateTime 2022/5/26
 */
public class ExpireTime {

    public static final  Long LIST_ARTICLE_CACHE_TIME=60*60*10000l;
    public static final  Long LIST_HOT_ARTICLE_CACHE_TIME=60*60*10000l;
    public static final  Long LIST_ARCHIVES_CACHE_TIME=30*60*10000l;

    public static final  Long SINGLE_ARTICLE_CACHE_TIME=24*60*60*10000l;


}
