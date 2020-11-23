package com.yuxiang.edu.service.statistics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: yuxiang
 * @Date: 2020/11/22 14:41
 */
@ComponentScan("com.yuxiang.edu")
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class StatisticsServiceMain {

    public static void main(String[] args) {
        SpringApplication.run(StatisticsServiceMain.class, args);
    }
}
