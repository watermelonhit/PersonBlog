package com.watermelonhit.blog.service.Imp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.watermelonhit.blog.dao.SysUser;
import com.watermelonhit.blog.mapper.SysUserMapper;
import com.watermelonhit.blog.service.LoginService;
import com.watermelonhit.blog.service.SysUserService;
import com.watermelonhit.blog.vo.ErrorCode;
import com.watermelonhit.blog.vo.LoginUserVo;
import com.watermelonhit.blog.vo.Result;
import com.watermelonhit.blog.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author watermelonhit
 * @DateTime 2021/8/26
 */
@Service
public class SysUserServiceImp implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private LoginService loginService;
    @Override
    public SysUser getSysUserById(long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        if(sysUser==null){
            sysUser=new SysUser();
            sysUser.setNickname("watermelonhit");
        }
        return sysUser;
    }

    @Override
    public SysUser findUser(String account, String password) {
        LambdaQueryWrapper<SysUser> sysUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysUserLambdaQueryWrapper.eq(SysUser::getAccount,account);
        sysUserLambdaQueryWrapper.eq(SysUser::getPassword,password);
        sysUserLambdaQueryWrapper.select(SysUser::getAvatar,SysUser::getId,SysUser::getNickname,SysUser::getAccount);
        sysUserLambdaQueryWrapper.last("limit 1");
        return sysUserMapper.selectOne(sysUserLambdaQueryWrapper);
    }

    /**
     * 根据token返回用户信息（登录后用的）
     * @param token
     * @return
     */
    @Override
    public Result getCurrentUser(String token) {
        SysUser sysUser=loginService.checkToken(token);
        if(sysUser==null)return Result.fail(ErrorCode.TOKEN_ERROR.getCode(),ErrorCode.TOKEN_ERROR.getMsg() );
        LoginUserVo loginUserVo = new LoginUserVo();
        BeanUtils.copyProperties(sysUser,loginUserVo);
        loginUserVo.setId(String.valueOf(sysUser.getId()));
        return Result.success(loginUserVo);
    }

    @Override
    public SysUser findUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getAccount,account);
        wrapper.last("limit 1");
        return sysUserMapper.selectOne(wrapper);
    }

    @Override
    public void save(SysUser sysUser) {
    sysUserMapper.insert(sysUser);
    }

    @Override
    public UserVo getAuthorVoById(Long authorId) {
        SysUser sysUser = sysUserMapper.selectById(authorId);
        if(sysUser==null){
            sysUser=new SysUser();
            sysUser.setId(1L);
            sysUser.setAvatar("/static/img/logo.b3a48c0.png");
            sysUser.setNickname("watermelonhit");
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(sysUser,userVo);
        userVo.setId(String.valueOf(sysUser.getId()));
        return userVo;
    }

    @Override
    public void updateUserById(SysUser user) {
        sysUserMapper.updateById(user);
    }
}
