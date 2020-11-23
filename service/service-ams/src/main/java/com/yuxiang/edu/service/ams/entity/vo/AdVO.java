package com.yuxiang.edu.service.ams.entity.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: yuxiang
 * @Date: 2020/11/22 13:39
 */
@Data
public class AdVO implements Serializable {

    private static final long serialVersionUID = 2395045117531148517L;
    private String id;
    private String title;
    private Integer sort;
    private String type;
}