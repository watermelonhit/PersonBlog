package com.watermelonhit.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.watermelonhit.blog.dao.Board;
import com.watermelonhit.blog.vo.BoardVo;
import com.watermelonhit.blog.vo.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author watermelonhit
 * @DateTime 2022/8/24
 */
public interface BoardService extends IService<Board> {
    
    List<BoardVo> findAll();

    Result saveBoard(String message, HttpServletRequest request);
}
