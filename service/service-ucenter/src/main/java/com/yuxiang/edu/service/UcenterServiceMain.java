package com.yuxiang.edu.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: yuxiang
 * @Date: 2020/11/15 9:52
 */
@ComponentScan("com.yuxiang.edu")
@EnableDiscoveryClient
@SpringBootApplication
public class UcenterServiceMain {

    public static void main(String[] args) {
        SpringApplication.run(UcenterServiceMain.class);
    }
}
