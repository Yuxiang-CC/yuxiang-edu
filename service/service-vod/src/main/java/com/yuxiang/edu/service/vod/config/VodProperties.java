package com.yuxiang.edu.service.vod.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @Author: yuxiang
 * @Date: 2020/11/17 16:08
 */
@Data
@Component
@RefreshScope
public class VodProperties {

    @Value("${aliyun.vod.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.vod.accessKeySecret}")
    private String accessKeySecret;

    @Value("${aliyun.vod.templateGroupId}")
    private String templateGroupId;

    @Value("${aliyun.vod.workflowId}")
    private String workflowId;
}
