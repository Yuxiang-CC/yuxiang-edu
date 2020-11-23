package com.yuxiang.edu.service.vod.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: yuxiang
 * @Date: 2020/11/22 22:31
 */
@Data
public class LiveCategoryVO implements Serializable {

    private String id;

    private String parentId;

    private String name;

    private List<LiveCategoryVO> children = new ArrayList<>();

}
