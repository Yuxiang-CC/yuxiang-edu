package com.yuxiang.edu.service.statistics.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

/**
 * @Author: yuxiang
 * @Date: 2020/12/1 15:57
 */

@Data
@ContentRowHeight(18)
@HeadRowHeight(30)
@ColumnWidth(25)

public class DownLoadDaily {

    @ExcelProperty(value = "统计日期", index = 0)
    private String dateCalculated;

    @ExcelProperty(value = "注册人数", index = 1)
    private Integer registerNum;

    @ExcelProperty(value = "登录人数", index = 2)
    private Integer loginNum;

    @ExcelProperty(value = "播放视频数", index = 3)
    private Integer videoViewNum;

    @ExcelProperty(value = "新增课程数", index = 4)
    private Integer courseNum;
}
