package com.watermelonhit.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.watermelonhit.blog.dao.dos.Archive;

import java.util.List;

/**
 * @Author watermelonhit
 * @DateTime 2021/8/26
 */
public interface ArchiveMapper extends BaseMapper<Archive> {
   List<Archive> listArchives();
}
