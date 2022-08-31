package com.watermelonhit.blog.interceptor;

import com.alibaba.fastjson.JSON;
import com.watermelonhit.blog.dao.SysUser;
import com.watermelonhit.blog.service.LoginService;
import com.watermelonhit.blog.util.UserThreadLocal;
import com.watermelonhit.blog.vo.ErrorCode;
import com.watermelonhit.blog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author watermelonhit
 * @DateTime 2021/8/27
 */
@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断是否访问方法，如果不是（比如资源）则放行
        boolean isM = handler instanceof HandlerMethod;
        if (!isM) return true;
        //判断token是否为空
        String token = request.getHeader("Authorization");
        //打印日志
        log.info("=================request start===========================");
        String requestURI = request.getRequestURI();
        log.info("request uri:{}", requestURI);
        log.info("request method:{}", request.getMethod());
        log.info("token:{}", token);
        log.info("=================request end===========================");

        if (StringUtils.isBlank(token)) {
            Result fail = Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
            //返回json数据
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(fail));
            return false;
        }
        //校验token是否合法
        SysUser sysUser = this.loginService.checkToken(token);
        if (sysUser == null) {
            Result fail = Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
            //返回json数据
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(fail));
            return false;
        }
        UserThreadLocal.set(sysUser);
        //合法，放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //不清除会有内存泄露风险
        /**
         每一个Thread维护一个ThreadLocalMap, key为使用**弱引用**的ThreadLocal实例，value为线程变量的副本。
         **强引用**，使用最普遍的引用，一个对象具有强引用，不会被垃圾回收器回收。当内存空间不足，Java虚拟机宁愿抛出OutOfMemoryError错误，使程序异常终止，也不回收这种对象。
         **如果想取消强引用和某个对象之间的关联，可以显式地将引用赋值为null，这样可以使JVM在合适的时间就会回收该对象。**
         **弱引用**，JVM进行垃圾回收时，无论内存是否充足，都会回收被弱引用关联的对象。在java中，用java.lang.ref.WeakReference类来表示。
         简单点说就是currentThread生命周期大于Threadlocal，进行垃圾回收时Threadlocal就被回收了，而这个key对应的value还存在，故内存泄露了
         */
        UserThreadLocal.remove();
    }
}
