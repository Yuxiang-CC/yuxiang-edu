package com.yuxiang.edu.service.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: yuxiang
 * @Date: 2020/11/16 16:57
 */
@ComponentScan("com.yuxiang.edu")
@EnableDiscoveryClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SmsServiceMain {

    public static void main(String[] args) {
        SpringApplication.run(SmsServiceMain.class, args);
    }
}
