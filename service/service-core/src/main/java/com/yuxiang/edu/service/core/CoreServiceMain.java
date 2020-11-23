package com.yuxiang.edu.service.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: yuxiang
 * @Date: 2020/11/19 11:25
 */
@ComponentScan("com.yuxiang.edu")
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class CoreServiceMain {

    public static void main(String[] args) {

        SpringApplication.run(CoreServiceMain.class, args);
    }
}
