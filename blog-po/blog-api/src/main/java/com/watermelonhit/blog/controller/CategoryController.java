package com.watermelonhit.blog.controller;

import com.watermelonhit.blog.service.CategoryService;
import com.watermelonhit.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author watermelonhit
 * @DateTime 2021/8/27
 */
@RestController
@RequestMapping("categorys")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 在编写文章时返回所有的分类
     * @return
     */
    @GetMapping
    public Result getAllCategorys(){
        return categoryService.getAllCategorys();
    }

    /**
     * 导航-文章所有分类
     * @return
     */
    @GetMapping("/detail")
    public Result getAllCategorysDetail(){
        return categoryService.getAllCategorysDetail();
    }
    /**
     * 导航-文章所有分类,点进去后显示的图片
     * @return
     */
    @GetMapping("/detail/{id}")
    public Result getCategorysDetailById(@PathVariable("id") Long id){
        return categoryService.getCategorysDetailById(id);
    }
}
