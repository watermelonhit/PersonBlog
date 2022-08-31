package com.watermelonhit.blog.vo.params;

import com.watermelonhit.blog.vo.CategoryVo;
import com.watermelonhit.blog.vo.TagVo;
import lombok.Data;

import java.util.List;

/**
 * @Author watermelonhit
 * @DateTime 2021/8/28
 */
@Data
public class ArticleParam {

    private Long id;

    private ArticleBodyParam body;

    private CategoryVo category;

    private String summary;

    private List<TagVo> tags;
    // 新建的标签
    private List<String> dynamicTags;

    private  String search;

    private String title;
}

