package com.watermelonhit.blog.service;

import com.watermelonhit.blog.vo.Result;
import com.watermelonhit.blog.vo.TagVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author watermelonhit
 * @DateTime 2021/8/25
 */

public interface TagService {
    public List<TagVo>  getTagVoListByArticleId(Long id);

    Result getHotTagsId(int limit);

    Result getAllTags();

    Result getAllTagsDetail();

    Result getTagsDetailById(Long id);
}
