package com.watermelonhit.blog.handler;

import com.watermelonhit.blog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author watermelonhit
 * @DateTime 2021/8/26
 */
@ControllerAdvice
@Slf4j
public class AllExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result doException(Exception exception) {
        exception.printStackTrace();
        log.error("异常消息=========> " + exception.getMessage() + "\ncause======>" + exception.getCause());
        return Result.fail(-999, "后台出现异常");
    }
}
