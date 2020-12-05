package com.yuxiang.edu.service.mms;

import com.yuxiang.edu.service.mms.utils.SpringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Author: yuxiang
 * @Date: 2020/11/27 16:37
 */
@ComponentScan(value = "com.yuxiang.edu")
@EnableDiscoveryClient
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class MmsServiceMain {

    @Bean
    SpringUtils springUtils() {
        return new SpringUtils();
    }

    public static void main(String[] args) {
        SpringApplication.run(MmsServiceMain.class, args);
    }
}
