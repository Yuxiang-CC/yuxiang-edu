package com.yuxiang.edu.service.vod.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

/**
 * @Author: yuxiang
 * @Date: 2020/11/26 16:25
 */
@Data
@ContentRowHeight(18)
@HeadRowHeight(30)
@ColumnWidth(25)

public class DownLoadLiveCategorytData {

    @ExcelProperty(value = "一级分类", index = 0)
    private String levelOneTitle;

    @ExcelProperty(value = "二级分类", index = 1)
    private String levelTwoTitle;
}
