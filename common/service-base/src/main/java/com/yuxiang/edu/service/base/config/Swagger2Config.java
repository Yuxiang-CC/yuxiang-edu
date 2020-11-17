package com.yuxiang.edu.service.base.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author: yuxiang
 * @Date: 2020/11/15 13:52
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    /**
     * 向外部提供的Api
     * @return
     */
    @Bean
    public Docket wepApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")
                .apiInfo(webApiInfo())
                .select()
                .paths(Predicates.and(PathSelectors.regex("/api/.*")))
                .build();
    }
    private ApiInfo webApiInfo() {
        return new ApiInfoBuilder()
                .title("API文档")
                .description("在线教育网站API接口")
                .version("1.0")
                .contact(new Contact("Yuxiang", "http://yuxiangai.cn", "Ren666665@163.com"))
                .build();
    }

    /**
     * 管理员Api
     * @return
     */
    @Bean
    public Docket adminApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("adminApi")
                .apiInfo(adminApiInfo())
                .select()
                .paths(Predicates.and(PathSelectors.regex("/admin/.*")))
                .build();
    }

    private ApiInfo adminApiInfo() {
        return new ApiInfoBuilder()
                .title("后台管理的API文档")
                .description("在线教育网站后台管理API接口")
                .version("1.0")
                .contact(new Contact("Yuxiang", "http://yuxiangai.cn", "Ren666665@163.com"))
                .build();
    }

}
