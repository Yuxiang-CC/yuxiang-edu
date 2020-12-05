package com.yuxiang.edu.service.trade.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuxiang.edu.service.trade.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yuxiang.edu.service.trade.entity.vo.OrderQueryVO;

import java.util.List;

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
     * @param memberId
     * @return
     */
    Order getOrderById(String orderId, String memberId);

    /**
     * 判断是否购买此课程
     * @param courseId
     * @param id
     * @return
     */
    boolean isBuyByCourseId(String courseId, String id);

    /**
     * 根据会员ID获取订单列表
     * @param memberId
     * @return
     */
    List<Order> getListByMemberId(String memberId);

    /**
     * 根据会员ID删除订单
     * @param orderId
     * @param memberId
     * @return
     */
    boolean removeById(String orderId, String memberId);

    /**
     * 分页查询订单列表
     * @param pageParam
     * @param orderQueryVO
     * @return
     */
    Page<Order> selectPage(Page<Order> pageParam, OrderQueryVO orderQueryVO);

    /**
     * 根据订单唯一ID获取订单信息
     * @param orderNo
     * @param memberId
     * @return
     */
    Order getOrderNoById(String orderNo, String memberId);
}
