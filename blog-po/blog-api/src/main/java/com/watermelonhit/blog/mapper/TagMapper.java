package com.watermelonhit.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.watermelonhit.blog.dao.Tag;

import java.util.List;

/**
 * @Author watermelonhit
 * @DateTime 2021/8/25
 */

public interface TagMapper extends BaseMapper<Tag> {
    List<Tag> getTagVoListByArticleId(Long id);

    List<Long> getTagsIdByArticles(int limit);

    List<Tag> getTagsByIds(List<Long> tagIds);

    int deleteTagsByArticleId(Long id);
}
