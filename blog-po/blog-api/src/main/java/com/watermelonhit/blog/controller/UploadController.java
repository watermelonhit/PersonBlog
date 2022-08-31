package com.watermelonhit.blog.controller;

import com.watermelonhit.blog.util.QiniuUtils;
import com.watermelonhit.blog.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * @Author watermelonhit
 * @DateTime 2021/8/28
 */
@RestController
@RequestMapping("upload")
public class UploadController {
    @Autowired
    private QiniuUtils qiniuUtils;
    @PostMapping
    public Result upload(@RequestParam("image")MultipartFile multipartFile){
       //生成唯一文件名
        String fileName = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(multipartFile.getOriginalFilename(), ".");
        boolean upload = qiniuUtils.upload(multipartFile, fileName);
        if (upload){
            return Result.success(qiniuUtils.getUrl() + fileName);
        }

        else return Result.fail(20001,"图片上传失败");
    }
}
