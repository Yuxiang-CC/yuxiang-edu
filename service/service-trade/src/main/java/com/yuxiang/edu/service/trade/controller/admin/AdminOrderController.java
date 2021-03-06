package com.yuxiang.edu.service.trade.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuxiang.edu.common.result.R;
import com.yuxiang.edu.service.trade.entity.Order;
import com.yuxiang.edu.service.trade.entity.vo.OrderQueryVO;
import com.yuxiang.edu.service.trade.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: yuxiang
 * @Date: 2020/11/26 17:12
 */
@Api(description = "订单管理控制器")
@RestController
@RequestMapping("/admin/trade/order")
@Slf4j
public class AdminOrderController {

    @Autowired
    private OrderService orderService;


    @ApiOperation("分页获取订单")
    @GetMapping("/list/{page}/{size}")
    public R listPage(@ApiParam(value = "当前页码", required = true) @PathVariable("page") Long page,
                      @ApiParam(value = "页面大小", required = true) @PathVariable("size") Long size,
                      @ApiParam("讲师列表查询对象") OrderQueryVO orderQueryVO) {

        Page<Order> pageParam = new Page<>(page, size);
        Page<Order> pageModelList = orderService.selectPage(pageParam, orderQueryVO);

        return R.ok().data("rows", pageModelList.getRecords())
                .data("total", pageModelList.getTotal());
    }

    @ApiOperation("根据订单ID获取订单信息")
    @ResponseBody
    @GetMapping("/info/{orderId}")
    public R get(@ApiParam("订单ID") @PathVariable("orderId") String orderId) {

        if (StringUtils.isBlank(orderId)) {
            return R.ok().data("item", null);
        }

        Order order = orderService.getById(orderId);
        return R.ok().data("item", order);
    }
}
