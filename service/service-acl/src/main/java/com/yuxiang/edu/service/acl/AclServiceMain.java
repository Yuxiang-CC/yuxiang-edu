package com.yuxiang.edu.service.acl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: yuxiang
 * @Date: 2020/12/29 14:46
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.yuxiang.edu")
public class AclServiceMain {

    public static void main(String[] args) {

        SpringApplication.run(AclServiceMain.class, args);
    }
}
