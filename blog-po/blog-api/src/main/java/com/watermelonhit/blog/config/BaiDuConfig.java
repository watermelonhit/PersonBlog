package com.watermelonhit.blog.config;

import com.baidu.aip.nlp.AipNlp;
import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author watermelonhit
 * @DateTime 2022/8/31
 */
@Configuration
@Data
@ToString
public class BaiDuConfig {
    //设置APPID/AK/SK
    @Value("${baidu.appID}")
    private String APP_ID;
    @Value("${baidu.apiKey}")
    private String API_KEY;
    @Value("${baidu.secretKey}")
    private String SECRET_KEY;

    @Bean
    public AipNlp getAipNlp() {
        AipNlp client = new AipNlp(this.getAPP_ID(), this.getAPI_KEY(), this.getSECRET_KEY());
        // 可选：设置网络连接参数
        // 建立连接的超时时间（单位：毫秒）
        client.setConnectionTimeoutInMillis(2000);
        // 通过打开的连接传输数据的超时时间（单位：毫秒）
        client.setSocketTimeoutInMillis(60000);
        return client;
    }

}
