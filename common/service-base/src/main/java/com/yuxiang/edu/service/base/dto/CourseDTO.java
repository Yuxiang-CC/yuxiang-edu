package com.yuxiang.edu.service.base.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: Yuxiang
 * @create: 2020-07-02
 **/
@Data
public class CourseDTO implements Serializable {

    /**
     * 课程ID
     */
    private String id;

    /**
     * 课程标题
     */
    private String title;

    /**
     * 课程销售价格，为0则可以免费观看
     */
    private BigDecimal price;

    /**
     * 课程封面图片路径
     */
    private String cover;

    /**
     * 课程讲师
     */
    private String teacherName;

}
