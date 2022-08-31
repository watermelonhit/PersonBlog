package com.watermelonhit.blog.service.Imp;

import com.alibaba.fastjson.JSON;
import com.watermelonhit.blog.dao.SysUser;
import com.watermelonhit.blog.service.LoginService;
import com.watermelonhit.blog.service.SysUserService;
import com.watermelonhit.blog.util.JWTUtils;
import com.watermelonhit.blog.util.UserThreadLocal;
import com.watermelonhit.blog.vo.ErrorCode;
import com.watermelonhit.blog.vo.Result;
import com.watermelonhit.blog.vo.UserVo;
import com.watermelonhit.blog.vo.params.LoginParams;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;


/**
 * @Author watermelonhit
 * @DateTime 2021/8/26
 */
@Service
@Slf4j
@Transactional//下面所有方法都是事务，确保方法能够执行，否则回滚
public class LoginServiceImp implements LoginService {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    //加密盐
    private static final String salt = "mszlusus!@#";

    @Override
    public Result loginUser(LoginParams loginParams) {
        String account = loginParams.getAccount();
        String password = loginParams.getPassword();
        //检查参数是否合法（为空）
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        //对密码进行加密
        password = DigestUtils.md5Hex(password + salt);
        //检查是否存在
        SysUser sysUser = this.sysUserService.findUser(account, password);
        if (sysUser == null) {
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }
        log.info("用户信息：===>{}",sysUser.toString());
        //登录成功返回token
        String token = JWTUtils.createToken(sysUser.getId());
        // 更新最后登录时间
        sysUser.setLastLogin(System.currentTimeMillis());
        this.sysUserService.updateUserById(sysUser);
        //把sysUser存放入redis(一天），减轻数据库压力和再一次校验
        this.redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);
        return Result.success(token);
    }

    /**
     * 对用户的token进行校验
     *
     * @param token
     * @return
     */
    @Override
    public SysUser checkToken(String token) {
        //校验token是否为空
        if (StringUtils.isEmpty(token)) return null;
        //解析token是否存在
        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
        if (stringObjectMap == null) return null;
        //从redis进行校验获取对象
        String s = this.redisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isBlank(s)) return null;
        else return JSON.parseObject(s, SysUser.class);
    }

    @Override
    public Result logout(String token) {
        this.redisTemplate.delete("TOKEN_" + token);
        return Result.success(null);
    }

    /**
     * 用户注册
     *
     * @param loginParams
     * @return
     */
    @Override
    public Result registerUser(LoginParams loginParams) {
        String account = loginParams.getAccount();
        String password = loginParams.getPassword();
        String nickname = loginParams.getNickname();
        //检查参数是否为空
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password) || StringUtils.isBlank(nickname)) {
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        //检查账户是否存在
        SysUser sysUser = this.sysUserService.findUserByAccount(account);
        if (sysUser != null) {
            return Result.fail(ErrorCode.Account_Exist.getCode(), ErrorCode.Account_Exist.getMsg());
        }
        //填充用户信息
        sysUser = new SysUser();
        sysUser.setNickname(nickname);
        sysUser.setAccount(account);
        //注意密码加密保存
        sysUser.setPassword(DigestUtils.md5Hex(password + salt));
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setLastLogin(System.currentTimeMillis());
        //部署到云服务器上后注意修改这里
        int index = new Random().nextInt(8) + 1;
        String avatar = "/static/user/user_" + index + ".png";
        sysUser.setAvatar(avatar);
        sysUser.setAdmin(1); //1 为true
        sysUser.setDeleted(0); // 0 为false
        sysUser.setSalt(salt);
        sysUser.setStatus("");
        sysUser.setEmail("");
        //保存用户
        this.sysUserService.save(sysUser);
        //生成token,并保存到redis中
        String token = JWTUtils.createToken(sysUser.getId());
        this.redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(sysUser), 1, TimeUnit.DAYS);
        return Result.success(token);
    }

    /**
     * 修改用户信息（从redis和mysql一起修改）
     *
     * @param loginParams
     * @return
     */
    @Override
    @Transactional
    public Result updateUser(LoginParams loginParams) {
        if (StringUtils.isBlank(loginParams.getNickname()) && StringUtils.isBlank(loginParams.getPassword())) {
            return Result.fail(11111, "修改信息有误！");
        }
        SysUser sysUser = UserThreadLocal.get();
        Long id = sysUser.getId();
        SysUser user = this.sysUserService.getSysUserById(id);
        if (StringUtils.isNotBlank(loginParams.getNickname())) {
            user.setNickname(loginParams.getNickname());
        }
        if (StringUtils.isNotBlank(loginParams.getPassword())) {
            user.setPassword(DigestUtils.md5Hex(loginParams.getPassword() + salt));
        }
        // 修改redis
        String token = JWTUtils.createToken(sysUser.getId());
        // 把user存放入redis(一天），减轻数据库压力和再一次校验
        this.redisTemplate.opsForValue().set("TOKEN_" + token, JSON.toJSONString(user), 1, TimeUnit.DAYS);
        // 修改数据库
        this.sysUserService.updateUserById(user);
        UserVo userVo = new UserVo();
        userVo.setNickname(user.getNickname());
        return Result.success(user);
    }

    public static void main(String[] args) {
        System.out.println(DigestUtils.md5Hex("123456" + salt));
    }
}
