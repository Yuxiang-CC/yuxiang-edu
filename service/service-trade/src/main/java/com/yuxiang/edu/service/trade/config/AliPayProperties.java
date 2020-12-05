package com.yuxiang.edu.service.trade.config;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @Author: yuxiang
 * @Date: 2020/12/2 16:31
 */

@Component
@RefreshScope
public class AliPayProperties {

    /**
     * 应用ID，您的APPID，收款账号既是您的APPID对应的支付宝账号
     */

    public static String app_id = "2016103100781619";

    /**
     * 商户私钥
     */
//    @Value("${alipay.memrchantPrivateKey}")
    public static String merchant_private_key = "";

    /**
     * 支付宝公钥
     */
//    @Value("${alipay.alipayPublicKey}")
    public static String alipay_public_key = "";

    /**
     * 服务器异步通知页面路径
     * 需http://格式的完整路径，不能加?id=1这类自定义参数，必须外网可以访问
     */
    public static String notify_url = "http://4ddf9b24cccd.ngrok.io/api/trade/pay/get-nogify";

    /**
     * 页面跳转同步通知页面路径
     * 需http://格式的完整路径，不能加?id=1这类自定义参数，必须外网可以访问
     */
    public static String return_url = "http://4ddf9b24cccd.ngrok.io/api/trade/pay/get-return";


    /**
     * 签名方式
     */
    public static String sign_type = "RSA2";

    /**
     * 字符编码格式
     */
    public static String charset = "UTF-8";

    /**
     * 支付宝网关，注意使用的是沙箱的支付宝网关，与正常网关的区别是多了dev
     */
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";


}
