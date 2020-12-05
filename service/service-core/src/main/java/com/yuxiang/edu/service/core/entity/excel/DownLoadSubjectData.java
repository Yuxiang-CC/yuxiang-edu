package com.yuxiang.edu.service.core.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

/**
 * @Author: yuxiang
 * @Date: 2020/11/26 10:23
 */
@Data
@ContentRowHeight(18)
@HeadRowHeight(30)
@ColumnWidth(25)
public class DownLoadSubjectData {

    @ExcelProperty(value = "一级分类", index = 0)
    private String levelOneTitle;

    @ExcelProperty(value = "二级分类", index = 1)
    private String levelTwoTitle;

    @ExcelProperty(value = "课程数", index = 2)
    private String courseNum;
}
