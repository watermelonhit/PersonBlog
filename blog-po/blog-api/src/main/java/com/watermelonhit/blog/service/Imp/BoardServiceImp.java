package com.watermelonhit.blog.service.Imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.watermelonhit.blog.dao.Board;
import com.watermelonhit.blog.dao.SysUser;
import com.watermelonhit.blog.mapper.BoardMapper;
import com.watermelonhit.blog.rabbitmq.BlogMessageSender;
import com.watermelonhit.blog.service.BoardService;
import com.watermelonhit.blog.service.LoginService;
import com.watermelonhit.blog.util.IpUtils;
import com.watermelonhit.blog.vo.BoardVo;
import com.watermelonhit.blog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author watermelonhit
 * @DateTime 2022/8/24
 */
@Service
@Slf4j
public class BoardServiceImp extends ServiceImpl<BoardMapper, Board> implements BoardService {

    @Resource
    private BoardMapper boardMapper;

    @Resource
    private LoginService loginService;

    @Resource
    private BlogMessageSender blogMessageSender;

    @Override
    public List<BoardVo> findAll() {
        List<BoardVo> allLegalBoards = this.boardMapper.getAllLegalBoards();
        return allLegalBoards;
    }

    @Override
    public Result saveBoard(String message, HttpServletRequest request) {
        // 用于查看该留言是否处于用户登录状态
        String token = request.getHeader("Authorization");
        SysUser sysUser = null;
        if (!token.equals("undefined")) {
            sysUser = this.loginService.checkToken(token);
        }

        Board board = new Board();
        if (sysUser != null) {
            board.setAuthorId(sysUser.getId());
        }
        board.setContext(message);
        // 留言状态默认成功，后期加入审核
        short statue = 1;
        board.setStatue(statue);
        String ip = IpUtils.getIpAddr(request);
        board.setIp(ip);
        // 插入数据库
        this.save(board);
        // 更新缓存
        this.blogMessageSender.sendUpdateBoardMessage();
        return Result.success(board.getId());
    }
}
