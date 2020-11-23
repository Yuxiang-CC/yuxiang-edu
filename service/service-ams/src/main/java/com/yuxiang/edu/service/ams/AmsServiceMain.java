package com.yuxiang.edu.service.ams;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: yuxiang
 * @Date: 2020/11/22 13:33
 */
@SpringBootApplication
@ComponentScan("com.yuxiang.edu")
@EnableDiscoveryClient
@EnableFeignClients
public class AmsServiceMain {

    public static void main(String[] args) {

        SpringApplication.run(AmsServiceMain.class, args);
    }
}
