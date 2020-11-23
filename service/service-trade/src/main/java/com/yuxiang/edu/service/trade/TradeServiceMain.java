package com.yuxiang.edu.service.trade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: yuxiang
 * @Date: 2020/11/22 14:42
 */
@ComponentScan("com.yuxiang.edu")
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class TradeServiceMain {

    public static void main(String[] args) {
        SpringApplication.run(TradeServiceMain.class, args);
    }
}
