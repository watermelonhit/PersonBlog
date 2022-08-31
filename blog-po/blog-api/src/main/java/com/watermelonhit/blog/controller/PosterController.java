package com.watermelonhit.blog.controller;

import com.google.gson.Gson;
import com.watermelonhit.blog.enums.BlogRedisKey;
import com.watermelonhit.blog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * @Author watermelonhit
 * @DateTime 2022/8/27
 */
@RestController
@RequestMapping("poster")
@Slf4j
public class PosterController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private Gson gson;

    @GetMapping("/getAll")
    public Result getAllPosters() {
        log.info("获取轮播图~");
        String jsonList = this.stringRedisTemplate.opsForValue().get(BlogRedisKey.BLOG_POSTERS.getKey());
        ArrayList arrayList = this.gson.fromJson(jsonList, ArrayList.class);
        return Result.success(arrayList);
    }


}
