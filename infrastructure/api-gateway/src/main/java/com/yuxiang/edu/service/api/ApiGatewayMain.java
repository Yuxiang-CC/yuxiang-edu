package com.yuxiang.edu.service.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: yuxiang
 * @Date: 2020/11/23 16:15
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayMain {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayMain.class, args);
    }
}
