package com.watermelonhit.blog.common.aop;

import java.lang.annotation.*;

/**
 * @Author watermelonhit
 * @DateTime 2021/8/28
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {
    String module() default "";
    String operator() default "";
}
