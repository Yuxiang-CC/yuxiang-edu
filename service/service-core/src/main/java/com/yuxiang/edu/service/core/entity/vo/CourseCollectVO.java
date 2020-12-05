package com.yuxiang.edu.service.core.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: yuxiang
 * @Date: 2020/11/24 21:10
 */
@Data
public class CourseCollectVO implements Serializable {

    private String id;
    private String title;
    private BigDecimal price;
    private String cover;
    private String teacherName;
    private Date gmtCreate;

}
