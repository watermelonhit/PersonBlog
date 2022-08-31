package com.watermelonhit.blog.vo.params;

import lombok.Data;

/**
 * @Author watermelonhit
 * @DateTime 2021/8/25
 */
@Data
public class PageParams {
    //当前页面（默认是一）
    private Integer page=1;
    //每一页显示多少条
    private Integer pageSize=10;

    private Long categoryId;

    private Long tagId;

    private String sort;

    private String year;

    private String month;
 //sql生成日期中月为01，02等
    public String getMonth(){
        if (this.month != null && this.month.length() == 1){
            return "0"+this.month;
        }
        return this.month;
    }

}
