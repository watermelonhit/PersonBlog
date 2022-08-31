package com.watermelonhit.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @Author watermelonhit
 * @DateTime 2021/8/25
 */
//@MapperScan(basePackages ={"com.watermelonhit.blog.mapper"})
@SpringBootApplication
public class BlogApp {
    public static void main(String[] args) {
        SpringApplication.run(BlogApp.class, args);
    }
}
