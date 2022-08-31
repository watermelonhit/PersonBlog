package com.watermelonhit.blog.common.aop;

import java.lang.annotation.*;

/**
 * @Author watermelonhit
 * @DateTime 2021/8/28
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cache {

    long expire() default 1 * 60 * 1000;

    String name() default "";

}