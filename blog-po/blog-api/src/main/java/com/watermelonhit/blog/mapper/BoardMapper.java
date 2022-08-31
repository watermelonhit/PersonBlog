package com.watermelonhit.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.watermelonhit.blog.dao.Board;
import com.watermelonhit.blog.vo.BoardVo;

import java.util.List;

/**
 * @Author watermelonhit
 * @DateTime 2022/8/24
 */
public interface BoardMapper extends BaseMapper<Board> {
    List<BoardVo> getAllLegalBoards();
}
