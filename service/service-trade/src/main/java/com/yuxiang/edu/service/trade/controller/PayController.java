package com.yuxiang.edu.service.trade.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuxiang.edu.common.result.R;
import com.yuxiang.edu.common.result.ResultCodeEnum;
import com.yuxiang.edu.common.util.JWTUtils;
import com.yuxiang.edu.common.util.JwtInfo;
import com.yuxiang.edu.service.trade.config.AliPayProperties;
import com.yuxiang.edu.service.trade.entity.Order;
import com.yuxiang.edu.service.trade.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author: yuxiang
 * @Date: 2020/12/2 16:51
 */
@Api(description = "支付管理控制器")
@Controller
@RequestMapping("/api/trade/pay")
@Slf4j
public class PayController {

    @Autowired
    private AlipayClient alipayClient;

    @Autowired
    private AlipayTradePagePayRequest alipayTradePagePayRequest;

    @Autowired
    private OrderService orderService;

    @ResponseBody
    @ApiOperation("向支付宝提供支付数据")
    @RequestMapping("/auth/pay")
    public R pay(@ApiParam("订单号") @RequestBody String orderNo,
                 HttpServletRequest request) throws AlipayApiException, IOException {
        System.out.println("orderNo:" + orderNo);
        JSONObject parseObject = JSON.parseObject(orderNo);
        String id = (String) parseObject.get("orderNo");
        // 根据课程ID和用户ID 获取支付信息
        JwtInfo info = JWTUtils.getInfoFromJWT(request);
        Order orderInfo = orderService.getOrderNoById(id, info.getId());
        if (orderInfo == null) {
            System.out.println("失败");
            return R.setResult(ResultCodeEnum.ALIPAY_PARAME_ERROR);
        } else {

            // 1.设置支付宝服务器返回访问地址
            alipayTradePagePayRequest.setNotifyUrl(AliPayProperties.notify_url);
            alipayTradePagePayRequest.setReturnUrl(AliPayProperties.return_url);
            // 2.设置请求参数（传递给支付宝服务器的数据）
            alipayTradePagePayRequest.setBizContent(
                    "{\"out_trade_no\":\""+ orderInfo.getOrderNo() +"\","
                            + "\"total_amount\":\""+ orderInfo.getTotalFee().divide(new BigDecimal("100")) +"\","
                            + "\"subject\":\""+ orderInfo.getCourseTitle() +"\","
                            + "\"body\":\""+ orderInfo.getTeacherName() +"\","
                            + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

            // 3.发送请求
            AlipayTradePagePayResponse result = alipayClient.pageExecute(alipayTradePagePayRequest, "get");
            if (result.isSuccess()) {
                System.out.println("成功");
                String body = result.getBody();
                System.out.println(body);
                return R.ok().data("url", body);
            } else {
                System.out.println("失败");
                return R.setResult(ResultCodeEnum.ALIPAY_CALL_ERROR);
            }
        }


    }

    @ApiOperation("返回地址")
    @RequestMapping("/get-return")
    public String returnUrl(HttpServletRequest request, HttpServletResponse response) throws Exception{
        System.out.println("/get-return");
        //获取支付宝GET过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        Iterator<String> iter = requestParams.keySet().iterator();
        while(iter.hasNext()){
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        // TODO 将支付记录，进行保存
        params.forEach((k,v) -> {
            System.out.println("key:" + k + ", value:" + v);
        });

        //RSA2验证
        boolean signVerified = AlipaySignature.rsaCheckV1(params,
                AliPayProperties.alipay_public_key, AliPayProperties.charset, AliPayProperties.sign_type);
        //调用SDK验证签名
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        //——请在这里编写您的程序（以下代码仅作参考）——
        if(signVerified) {
            System.out.println("验签成功");
            //商户订单号
            String out_trade_no = params.get("out_trade_no");
            //支付宝交易号
            String trade_no = params.get("trade_no");
            //付款金额
            String total_amount = params.get("total_amount");
            // 验签成功，更改订单信息
            Order order = new Order();
            order.setPayType(2);
            order.setStatus(1);
            QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("order_no", out_trade_no);
            queryWrapper.eq("status", 0);
            boolean resut = orderService.update(order, queryWrapper);
            if (!resut) {
                return "redirect:" + "http://localhost:3000/error/pay";
            }

            // 重定向到用户订单界面
            return "redirect:" + "http://localhost:3000/ucenter/order";
        }else {
            System.out.println("验签失败");
            //商户订单号
            String out_trade_no = request.getParameter("out_trade_no");
            //支付宝交易号
            String trade_no = request.getParameter("trade_no");
            //付款金额
            String total_amount = request.getParameter("total_amount");

            return "redirect:" + "http://localhost:3000/error/pay";
        }
    }

    @ApiOperation("返回地址")
    @RequestMapping("/get-notify")
    public String notifyUrl(HttpServletRequest request, HttpServletResponse response) throws Exception{
        System.out.println("/get-notify");
        // 获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        Iterator<String> iter = requestParams.keySet().iterator();
        while(iter.hasNext()){
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        //调用SDK验证签名
        boolean signVerified = AlipaySignature.rsaCheckV1(params,
                AliPayProperties.alipay_public_key, AliPayProperties.charset, AliPayProperties.sign_type);
        //——请在这里编写您的程序（以下代码仅作参考）——
        /* 实际验证过程建议商户务必添加以下校验：
            1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
            2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
            3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方
            （有的时候，一个商户可能有多个seller_id/seller_email）
            4、验证app_id是否为该商户本身。
        */
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        if(signVerified) {
            //验证成功
            //商户订单号
            String out_trade_no =request.getParameter("out_trade_no");
            //支付宝交易号
            String trade_no = request.getParameter("trade_no");
            //交易状态
            String trade_status = request.getParameter("trade_status");
            if(trade_status.equals("TRADE_FINISHED")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序
                //注意：
                //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
            }else if (trade_status.equals("TRADE_SUCCESS")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序
                //注意：
                //付款完成后，支付宝系统发送该交易状态通知
            }

            // 验签成功，更改订单信息
            Order order = new Order();
            order.setPayType(2);
            order.setStatus(1);
            QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("order_no", trade_no);
            queryWrapper.eq("status", 0);
            boolean resut = orderService.update(order, queryWrapper);
            if (!resut) {
                return "redirect:" + "http://localhost:3000/error/pay";
            }

            // 重定向到用户订单界面
            return "redirect:" + "http://localhost:3000/ucenter/order";

//            out.println("success");
        }else {//验证失败
//            out.println("fail");
            //调试用，写文本函数记录程序运行情况是否正常
            //String sWord = AlipaySignature.getSignCheckContentV1(params);
            //AlipayConfig.logResult(sWord);

            return "redirect:" + "http://localhost:3000/error/pay";
        }
    }


}
