package com.yuxiang.edu.service.trade.service;

import com.yuxiang.edu.service.trade.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author yuxiang
 * @since 2020-11-22
 */
public interface OrderService extends IService<Order> {

    /**
     * 生成订单
     * @param courseId
     * @param id
     * @return
     */
    String saveOrder(String courseId, String id);

    /**
     * 获取订单信息
     * @param orderId
     * @param id
     * @return
     */
    Order getOrderById(String orderId, String id);

    /**
     * 判断是否购买此课程
     * @param courseId
     * @param id
     * @return
     */
    boolean isBuyByCourseId(String courseId, String id);
}
