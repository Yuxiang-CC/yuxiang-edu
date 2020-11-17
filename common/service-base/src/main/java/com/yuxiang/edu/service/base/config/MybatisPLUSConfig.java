package com.yuxiang.edu.service.base.config;

import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author: yuxiang
 * @Date: 2020/11/15 13:48
 * @Descrti
 */
@EnableTransactionManagement
@Configuration
@MapperScan(value = "com.yuxiang.edu.service.*.mapper")
public class MybatisPLUSConfig {

    /**
     *  乐观锁插件
     *  需要在实体类属性中添加@Version注解
     * @return
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

    /**
     * 分页插件
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
