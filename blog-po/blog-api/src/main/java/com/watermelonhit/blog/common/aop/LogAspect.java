package com.watermelonhit.blog.common.aop;


import com.watermelonhit.blog.util.HttpContextUtils;
import com.watermelonhit.blog.util.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * aop
 *
 * @Author watermelonhit
 * @DateTime 2021/8/28
 */
@Aspect
@Component
@Slf4j
public class LogAspect {
    //切点：使用了这个注解@LogAnnotation的方法
    @Pointcut("@annotation(com.watermelonhit.blog.common.aop.LogAnnotation)")
    public void pt() {
    }

    @Around("pt()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        //执行方法
        Object result = point.proceed();
        //执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        //保存日志
        this.recordLog(point, time);
        return result;
    }

    private void recordLog(ProceedingJoinPoint joinPoint, long time) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
        log.info("=====================log start================================");
        log.info("module:{}", logAnnotation.module());
        log.info("operation:{}", logAnnotation.operator());

        // 以下请在开发场景下开启
//        //请求的方法名
//        String className = joinPoint.getTarget().getClass().getName();
//        String methodName = signature.getName();
//        log.info("request method:{}",className + "." + methodName + "()");
//
//        //请求的参数
//        Object[] args = joinPoint.getArgs();
//        String params = JSON.toJSONString(args[0]);
//        log.info("params:{}",params);

        //获取request 设置IP地址
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        log.info("ip:{}", IpUtils.getIpAddr(request));
        log.info("excute time : {} ms", time);
        log.info("=====================log end================================");
    }

}
