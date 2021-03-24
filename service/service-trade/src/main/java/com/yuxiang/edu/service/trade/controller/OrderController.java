package com.yuxiang.edu.service.trade.controller;


import com.yuxiang.edu.common.result.R;
import com.yuxiang.edu.common.result.ResultCodeEnum;
import com.yuxiang.edu.common.util.JWTUtils;
import com.yuxiang.edu.common.util.JwtInfo;
import com.yuxiang.edu.service.base.exception.RException;
import com.yuxiang.edu.service.trade.entity.Order;
import com.yuxiang.edu.service.trade.feign.CoreCourseService;
import com.yuxiang.edu.service.trade.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author yuxiang
 * @since 2020-11-22
 */
@Api(description = "订单管理控制器")
@Controller
@RequestMapping("/api/trade/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;


    @Autowired
    private CoreCourseService coreCourseService;


    @ApiOperation("新增订单")
    @ResponseBody
    @PostMapping("/auth/create/{courseId}")
    public R save(@PathVariable("courseId") String courseId, HttpServletRequest request) {

        JwtInfo infoFromJWT = JWTUtils.getInfoFromJWT(request);
        if (infoFromJWT == null) {
            throw new RException(ResultCodeEnum.TOKEN_ERROR);
        }

        String orderId = orderService.saveOrder(courseId, infoFromJWT.getId());

        return R.ok().data("orderId", orderId);
    }

    @ApiOperation("根据订单ID获取订单信息")
    @ResponseBody
    @GetMapping("/auth/get/{orderId}")
    public R get(@ApiParam("订单ID") @PathVariable("orderId") String orderId, HttpServletRequest request) {

        JwtInfo infoFromJWT = JWTUtils.getInfoFromJWT(request);
        Order order = orderService.getOrderById(orderId, infoFromJWT.getId());
        return R.ok().data("item", order);
    }


    @ApiOperation("判断课程是否购买")
    @ResponseBody
    @GetMapping("/auth/is-buy/{courseId}")
    public R isBuy(@ApiParam("订单ID") @PathVariable("courseId") String courseId, HttpServletRequest request) {

        JwtInfo infoFromJWT = JWTUtils.getInfoFromJWT(request);
        boolean isBuy = orderService.isBuyByCourseId(courseId, infoFromJWT.getId());
        return R.ok().data("isBuy", isBuy);
    }

    @ApiOperation("查询用户订单")
    @ResponseBody
    @GetMapping("/auth/list")
    public R getList(HttpServletRequest request) {

        JwtInfo infoFromJWT = JWTUtils.getInfoFromJWT(request);
        List<Order> orders =  orderService.getListByMemberId(infoFromJWT.getId());
        return R.ok().data("items", orders);
    }

    @ApiOperation("删除订单")
    @ResponseBody
    @DeleteMapping("/auth/remove/{orderId}")
    public R deleteOrder(@ApiParam("orderId") @PathVariable("orderId") String orderId ,
                         HttpServletRequest request) {

        JwtInfo infoFromJWT = JWTUtils.getInfoFromJWT(request);
        boolean result = orderService.removeById(orderId, infoFromJWT.getId());
        if (result) {
            return R.ok().message("删除成功");
        } else {
            return R.error().message("数据不存在");
        }

    }


    @ApiOperation("链路调用测试")
    @GetMapping("/sleuth/{param}")
    public R testSleuth(@PathVariable("param") String param) {

        System.out.println("链路调用开始。。。。" + param);
        return coreCourseService.testSleuth();
    }


}

