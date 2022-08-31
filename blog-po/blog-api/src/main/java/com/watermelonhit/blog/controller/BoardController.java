package com.watermelonhit.blog.controller;

import com.watermelonhit.blog.common.aop.Cache;
import com.watermelonhit.blog.service.BoardService;
import com.watermelonhit.blog.vo.BoardVo;
import com.watermelonhit.blog.vo.Result;
import com.watermelonhit.blog.vo.params.BoardParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author watermelonhit
 * @DateTime 2022/8/24
 */
@RestController
@RequestMapping("/board")
@Slf4j
public class BoardController {


    @Autowired
    private BoardService boardService;

    /**
     * 返回所有合法留言
     *
     * @return
     */
    @Cache(name = "留言板获取所有留言")
    @GetMapping("/getAll")
    public Result listBoards() {
        log.info("获取所有留言信息");
        List<BoardVo> boards = this.boardService.findAll();
        return Result.success(boards);
    }

    /**
     * 进行留言
     *
     * @param boardParam
     * @param request
     * @return
     */
    @PostMapping("/add")
    public Result saveBoard(@RequestBody BoardParam boardParam, HttpServletRequest request) {
        if (boardParam == null || StringUtils.isEmpty(boardParam.getContext())) {
            return Result.fail(407, "留言内容不能为空");
        }
        log.info("发表留言======>{}", boardParam.getContext());
        return this.boardService.saveBoard(boardParam.getContext(), request);
    }


}
