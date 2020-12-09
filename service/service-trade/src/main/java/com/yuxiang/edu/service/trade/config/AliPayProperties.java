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
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCUx1jxQ7AKL+ZKlMmGiWfEAasPVg5TxkfZLg8GoKkMHNGUmszMO29fUQaR9tcZQdDsh89GjDCnigk1tKvAkIOANP6Kqtj8GOUp+akFQ6f7EPzLspaCdeOM1x7SP5D7N1JojWoT+vp2n/psT8m7YD3ms3ZOsmCxzwqce9GTBMkhPH3tLEMAE2aIZkNuRVtc7VtcQPSat/XXJWJF3ComcKRy96SrKf/7T3St8x9pZmnkiB5us2lEfkTXPgxlzjJ3Xp7uk8kGBdtuiPBfU7iJngaBiQi2SKTYn6Z7z2p4AtJmFdXrvDidLiAGmigpkkfc4ArTl+OszEmV/lhu/hoS2AOvAgMBAAECggEAU2KDE/2m3Z7JCIXpiRDb6nww3pCRyRw5SpzI6SMEe/hu8kXsgI7TNe3Yq9hX6Bz+NoNLhbOu1UfFL6eKF4wnV6TcAOYOZQSi8+qsc3ne3RVBSf1W49mk5lvvN1FPNa1k75pKmNYyqGezWB9D5lPjfbiZlzPcg+uRe/DdnfOxsoEuUp7LqICLNmHiYkbWxn/q/w+nakAfv3U9rHZO0QRIFYQJ6iN6mI5SL3tlaE5ZGLms6/cSlBOFeBHJza8ezn2ZlQMlTTKognkNMM0D+llHEUNOzG7htZtCsTZbcw/mxyaZanRhO9KbSA8uLzOOXAJZB9o8JFexL9BvuqhBlZYMQQKBgQD6Vi6zht5ha8KHXVLlkuTkmIpqU9xZHuYR4e/8Epaw2nvdREGhtRLwgfr9Xzp+1DLK3XZdBHlTOSfNySRyYXYcahvQJr21f9XS3/FjrfdVezDjlyclFE8vrmrGz5zLnwL6HReAzd8QdZNJWC6Hl+9N8oiuCs7Noqg3F9mmtbciTwKBgQCYJP66uYOcbdzNoqpt1aO6nt4FAUUMU6CfXWRnSeSUV/qme754DDOBpKaPyPOruwqCpkB3VG67Pl8pS7DgdkfouOOJ4tRipMMpXGGi1MxaLg+3cy+zvh5Zw4KIwl1iou8H7mmIz/xqKNlimKUixga0p1QVQUgDZqajn049FfGQoQKBgQDiXxKGFzx6/hX/kF2I6ed/64ynD8Re9rj3jMcVu712mWuOr81lrDc/M1rCyd08mSiBF/iTrpsDrdiXlv8VS5qhoTYbaRz9xmaV9Rn0mHjjXK3d8G5ecloU9PTraQwReW4YvDvnpsLCwH96wHn69WJZqyr7rxxJCYBxqWiQ/xtmswKBgBhWGpTdpcq43A/cjCxjdvwb+2GLkXLmUZQMSUooTLLZ29MTiiUDtp1vK+FmAGwg0A6T703nKIs0793YKKPO57O3F1Zv/Q8GUd1k1I1KQqHOKl9qwl+a8pnjJxcfF/b2HwwCu8jRlAubGyKlTgzg5iOxfEamGZHJsrALhGVLBEfhAoGAY6HjEj967d22zZ6x/InQsBdsCn/2J3n9/s43YW+1kGRkjQecJlv5wpjaRLu/rM83X69e3IKyceL1c21pM80LRHFq4lQada7zy2K5FJmtrXNQ6Qms56bhT7oEqz9RRDRTonIZmkHiPNf1rt0vmr8pYsH4JWOS6d9qIpYnbnt4B/I=";

    /**
     * 支付宝公钥
     */
//    @Value("${alipay.alipayPublicKey}")
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjlxtpany58ljdRT34roSrfuYOlDporwswrPPQ/kHFVSpA2Wgl5xVmZE5nkor648yOAlZqLc4xb2DBDQ6wPqF2yVyopBqR26yVpWf3ewX31iVfIcUsjY6K0pD5nmOSaXDtKjepohQK71ergp+ECvWl+U2xJvbhtLLrx+kVDGsImM1ZrrULV0KShRyNq8K5Cc+KDnCFW0sucMi81LY5oW2McxvTY4a+Gr4LfsU+xB2PleIpmrQcEwykRW1SwGMNOA4yACxVJaU8b3HQ3MjsbfXFjMOCEX8nXcFuY3tupv+DcAWgnMBUbrpvGuS34LACgj+NpkUs+XoVMJ+dvvQPvI/WQIDAQAB";

    /**
     * 服务器异步通知页面路径
     * 需http://格式的完整路径，不能加?id=1这类自定义参数，必须外网可以访问
     */
    public static String notify_url = "http://bee4f48596f2.ngrok.io/api/trade/pay/get-nogify";

    /**
     * 页面跳转同步通知页面路径
     * 需http://格式的完整路径，不能加?id=1这类自定义参数，必须外网可以访问
     */
    public static String return_url = "http://bee4f48596f2.ngrok.io/api/trade/pay/get-return";


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
