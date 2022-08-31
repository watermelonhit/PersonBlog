package com.watermelonhit.blog.service;

import com.watermelonhit.blog.vo.CategoryVo;
import com.watermelonhit.blog.vo.Result;

/**
 * @Author watermelonhit
 * @DateTime 2021/8/27
 */
public interface CategoryService {
    public CategoryVo getCategoryById(Long id);

    Result getAllCategorys();

    Result getAllCategorysDetail();

    Result getCategorysDetailById(Long id);
}
