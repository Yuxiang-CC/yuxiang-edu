package com.yuxiang.edu.service.ucenter.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @Author: yuxiang
 * @Date: 2020/11/26 16:07
 */
@Data
@Component
@RefreshScope
public class WeiboProperties {

    @Value("${wb.app-key}")
    private String appKey;

    @Value("${wb.app-secret}")
    private String appSecret;
}
