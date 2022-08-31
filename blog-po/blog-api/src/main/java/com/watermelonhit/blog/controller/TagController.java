package com.watermelonhit.blog.controller;

import com.watermelonhit.blog.common.aop.Cache;
import com.watermelonhit.blog.service.TagService;
import com.watermelonhit.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author watermelonhit
 * @DateTime 2021/8/26
 */
@RestController
@RequestMapping("tags")
public class TagController {
    @Autowired
    private TagService tagService;

    /**
     *返回前limit个tag（以它所有的文章数进行排序）
     * @return
     */
    @GetMapping("/hot")
    @Cache(name = "首页最热标签")
    public Result getHotTags(){
        int limit=6;
        return tagService.getHotTagsId(limit);
    }

    @GetMapping
    public Result getAllTags(){
        return tagService.getAllTags();
    }

    /**
     * 导航-tag分类
     * @return
     */
    @GetMapping("/detail")
    public Result getAllTagsDetail(){
        return tagService.getAllTagsDetail();
    }
    @GetMapping("/detail/{id}")
    public Result getTagsDetailById(@PathVariable("id") Long id){
        return tagService.getTagsDetailById(id);
    }
}
