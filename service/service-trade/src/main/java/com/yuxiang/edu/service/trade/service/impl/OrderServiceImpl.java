package com.yuxiang.edu.service.trade.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuxiang.edu.common.result.ResultCodeEnum;
import com.yuxiang.edu.service.base.constant.TradeConstant;
import com.yuxiang.edu.service.base.dto.CourseDTO;
import com.yuxiang.edu.service.base.dto.MemberDTO;
import com.yuxiang.edu.service.base.exception.RException;
import com.yuxiang.edu.service.trade.entity.Order;
import com.yuxiang.edu.service.trade.entity.vo.OrderQueryVO;
import com.yuxiang.edu.service.trade.feign.CoreCourseService;
import com.yuxiang.edu.service.trade.feign.UcenterMemberService;
import com.yuxiang.edu.service.trade.mapper.OrderMapper;
import com.yuxiang.edu.service.trade.service.OrderService;
import com.yuxiang.edu.service.trade.util.OrderNoUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author yuxiang
 * @since 2020-11-22
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private CoreCourseService coreCourseService;

    @Autowired
    private UcenterMemberService ucenterMemberService;

    @Override
    public String saveOrder(String courseId, String memberId) {

        // 1.首先判断用户是否已经购买此课程
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        queryWrapper.eq("member_id", memberId);
        Order selectOneOrder = baseMapper.selectOne(queryWrapper);

        if (selectOneOrder != null) {
            // 如果订单存在直接返回订单ID
            return selectOneOrder.getId();
        }

        // 查询课程信息
        CourseDTO courseDTO = coreCourseService.getCourseDTOById(courseId);
        if (courseDTO == null) {
            throw new RException(ResultCodeEnum.PARAM_ERROR);
        }

        // 查询会员信息
        MemberDTO memberDTO = ucenterMemberService.getMemberDTOById(memberId);
        if (memberDTO == null) {
            throw new RException(ResultCodeEnum.PARAM_ERROR);
        }

        Order order = new Order();
        // 组装课程信息
        order.setCourseId(courseDTO.getId());
        order.setCourseCover(courseDTO.getCover());
        order.setCourseTitle(courseDTO.getTitle());
        order.setTeacherName(courseDTO.getTeacherName());
        // 乘于100 精确到分
        order.setTotalFee(courseDTO.getPrice().multiply(new BigDecimal(100)));

        // 组装用户信息
        order.setMemberId(memberDTO.getId());
        order.setMobile(memberDTO.getMobile());
        order.setNickname(memberDTO.getNickname());

        // 组装订单信息
        order.setOrderNo(OrderNoUtils.getOrderNo());
        // 0表示未支付，1表示已支付
        order.setStatus(0);
        // 1表示微信支付，2表示支付宝支付
        // order.setPayType(1);
        baseMapper.insert(order);

        return order.getId();
    }

    @Override
    public Order getOrderById(String orderId, String memberId) {

        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", orderId);
        queryWrapper.eq("member_id", memberId);

        Order order = baseMapper.selectOne(queryWrapper);
        return order;
    }

    @Override
    public boolean isBuyByCourseId(String courseId, String memberId) {

        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("course_id", courseId)
                .eq("member_id", memberId)
                .eq("status", 1);
        Integer integer = baseMapper.selectCount(queryWrapper);

        return integer.intValue() > 0;
    }

    @Override
    public List<Order> getListByMemberId(String memberId) {

        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");
        queryWrapper.eq("member_id", memberId);
        List<Order> orderList = baseMapper.selectList(queryWrapper);
        return orderList;
    }

    @Override
    public boolean removeById(String orderId, String memberId) {

        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", orderId)
                .eq("member_id", memberId);

        return this.remove(queryWrapper);
    }


    @Override
    public Page<Order> selectPage(Page<Order> pageParam, OrderQueryVO orderQueryVO) {

        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        // 1.按订单顺序排序
        queryWrapper.orderByDesc("id");
        if (orderQueryVO == null) {
            // 2.无条件查询
            return baseMapper.selectPage(pageParam, queryWrapper);
        }
        // 3.条件查询
        String teacherName = orderQueryVO.getTeacherName();
        String memberId = orderQueryVO.getMemberId();
        String mobile = orderQueryVO.getMobile();
        Integer payType = orderQueryVO.getPayType();
        Integer status = orderQueryVO.getStatus();

        if (StringUtils.isNotBlank(teacherName)) {
            queryWrapper.eq("teacher_name", teacherName);
        }
        if (StringUtils.isNotBlank(memberId)) {
            queryWrapper.eq("member_id", memberId);
        }
        if (StringUtils.isNotBlank(mobile)) {
            queryWrapper.eq("mobile", mobile);
        }
        if (payType != null) {
            if (payType.equals(TradeConstant.WEIXIN_PAY) || payType.equals(TradeConstant.ALI_PAY)) {
                queryWrapper.eq("pay_type", payType);
            }
        }
        if (status != null) {
            if (status.equals(TradeConstant.PAID_STATUS) || status.equals(0)) {
                queryWrapper.eq("status", status);
            }
        }
        return baseMapper.selectPage(pageParam, queryWrapper);
    }


    @Override
    public Order getOrderNoById(String orderNo, String memberId) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no", orderNo);
        queryWrapper.eq("member_id", memberId);

        Order order = baseMapper.selectOne(queryWrapper);
        return order;
    }
}
