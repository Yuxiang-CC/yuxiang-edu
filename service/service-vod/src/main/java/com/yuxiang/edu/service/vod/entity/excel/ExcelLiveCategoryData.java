package com.yuxiang.edu.service.vod.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @Author: yuxiang
 * @Date: 2020/11/23 11:14
 */

@Data
public class ExcelLiveCategoryData {

    @ExcelProperty("一级分类")
    private String levelOneTitle;

    @ExcelProperty("二级分类")
    private String levelTwoTitle;
}
