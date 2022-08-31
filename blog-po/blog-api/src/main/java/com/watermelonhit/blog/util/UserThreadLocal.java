package com.watermelonhit.blog.util;

import com.watermelonhit.blog.dao.SysUser;

/**
 * @Author watermelonhit
 * @DateTime 2021/8/27
 */
public class UserThreadLocal {
    private UserThreadLocal(){}
    private static final ThreadLocal<SysUser> threadLoacl=new ThreadLocal();
    public static SysUser get(){
        return threadLoacl.get();
    }
    public static void set(SysUser sysUser){
        threadLoacl.set(sysUser);
    }
    public static void remove(){
        threadLoacl.remove();
    }
}
