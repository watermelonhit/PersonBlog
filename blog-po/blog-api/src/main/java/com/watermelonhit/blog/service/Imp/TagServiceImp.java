package com.watermelonhit.blog.service.Imp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.watermelonhit.blog.dao.Tag;
import com.watermelonhit.blog.mapper.TagMapper;
import com.watermelonhit.blog.service.TagService;
import com.watermelonhit.blog.vo.Result;
import com.watermelonhit.blog.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author watermelonhit
 * @DateTime 2021/8/25
 */
@Service
public class TagServiceImp implements TagService {

    @Autowired
    private TagMapper tagMapper;

    /**
     * 根据文章id找到所有标签，由于mybatis不支持多表查询，所以得自己写xml
     * @param id
     * @return
     */
    @Override
    public List<TagVo> getTagVoListByArticleId(Long id) {
        List<Tag> tagList=tagMapper.getTagVoListByArticleId(id);
        List<TagVo> tagVos = copyList(tagList);
        return tagVos;
    }

    /**
     * 查询最火的前limit条tags
     * @param limit
     * @return
     */
    @Override
    public Result getHotTagsId(int limit) {
        List<Long>tagIds=tagMapper.getTagsIdByArticles(limit);
        //如果为空，则返回空集合
        if(CollectionUtils.isEmpty(tagIds)){
            return Result.success(Collections.emptyList());
        }
        List<Tag>tagList=tagMapper.getTagsByIds(tagIds);
        return Result.success(tagList);
    }

    @Override
    public Result getAllTags() {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Tag::getId,Tag::getTagName);
        List<Tag> tagList = tagMapper.selectList(queryWrapper);
        return Result.success(copyList(tagList));
    }

    @Override
    public Result getAllTagsDetail() {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        List<Tag> tagList = tagMapper.selectList(queryWrapper);
        return Result.success(copyList(tagList));
    }

    @Override
    public Result getTagsDetailById(Long id) {
        Tag tag = tagMapper.selectById(id);
        return Result.success(copy(tag));
    }

    public TagVo copy(Tag tag){
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        tagVo.setId(String.valueOf(tag.getId()));
        return tagVo;
    }
    public List<TagVo> copyList(List<Tag> tagList){
        List<TagVo> tagVoList = new ArrayList<>();
        for (Tag tag : tagList) {
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }
}
