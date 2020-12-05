package com.yuxiang.edu.service.trade.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: yuxiang
 * @Date: 2020/12/2 16:30
 */
@Configuration
public class AliPayConfig{

    /**
     * 创建支付宝客户端对象
     * @return
     */
    @Bean("alipayClient")
    public AlipayClient alipayClient() {
        DefaultAlipayClient client = new DefaultAlipayClient(
                AliPayProperties.gatewayUrl,
                AliPayProperties.app_id,
                AliPayProperties.merchant_private_key,
                "json",
                AliPayProperties.charset,
                AliPayProperties.alipay_public_key,
                AliPayProperties.sign_type);
        return client;
    }

    /**
     * 支付信息的配置
     * @return
     */
    @Bean
    public AlipayTradePagePayRequest alipayTradePagePayRequest() {

        return new AlipayTradePagePayRequest();
    }
}
