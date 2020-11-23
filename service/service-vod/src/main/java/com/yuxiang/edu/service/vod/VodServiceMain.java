package com.yuxiang.edu.service.vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: yuxiang
 * @Date: 2020/11/17 16:07
 */
@ComponentScan("com.yuxiang.edu")
@EnableDiscoveryClient
@SpringBootApplication
public class VodServiceMain {

    public static void main(String[] args) {

        SpringApplication.run(VodServiceMain.class, args);
    }
}
