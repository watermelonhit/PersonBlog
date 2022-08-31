package com.watermelonhit.blog.service.Imp;

import com.baidu.aip.nlp.AipNlp;
import com.watermelonhit.blog.service.ArticleAutoService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;


/**
 * @Author watermelonhit
 * @DateTime 2022/7/3
 */
@Slf4j
@Service
public class ArticleAutoServiceImp implements ArticleAutoService {

    @Resource
    private AipNlp client;

    // 此数值将作为摘要结果的最大长度。推荐最优区间：200-500字
    public final Integer MAX_SUMMARY_LEN = 80;

    @Override
    public String getAutoSummary(String title, String content) {

        // 传入可选参数调用接口
        HashMap<String, Object> options = new HashMap<String, Object>();
        options.put("title", title);
        // 新闻摘要接口
        JSONObject res = this.client.newsSummary(content, this.MAX_SUMMARY_LEN, options);
        log.info("成功调用了自动生成文章摘要，summaryId:{}", res.get("log_id"));
        return (String) res.get("summary");
    }
}
