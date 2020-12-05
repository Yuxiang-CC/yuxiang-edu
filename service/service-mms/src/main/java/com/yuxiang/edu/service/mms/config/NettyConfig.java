package com.yuxiang.edu.service.mms.config;

import com.yuxiang.edu.service.mms.netty.WebSocketServer;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author: yuxiang
 * @Date: 2020/11/28 14:51
 */
@Data
@Component
@ConfigurationProperties(prefix = "netty")
public class NettyConfig {

    private int port;

    @PostConstruct
    public void start() {
        WebSocketServer.getInstance().start(port);
    }
}
