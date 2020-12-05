package com.yuxiang.edu.service.statistics.controller.admin;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuxiang.edu.common.result.R;
import com.yuxiang.edu.common.result.ResultCodeEnum;
import com.yuxiang.edu.service.base.exception.RException;
import com.yuxiang.edu.service.statistics.entity.Daily;
import com.yuxiang.edu.service.statistics.entity.excel.DownLoadDaily;
import com.yuxiang.edu.service.statistics.service.DailyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @Author: yuxiang
 * @Date: 2020/11/23 14:35
 */
@Api(description = "统计控制器")
@RestController
@RequestMapping("/admin/statistics/daily")
@Slf4j
public class AdminDailyController {

    @Autowired
    private DailyService dailyService;

    @ApiOperation("根据日期成生统计信息")
    @GetMapping("/create/{day}")
    public R createStatisticsByDay(@ApiParam("统计日期") @PathVariable("day") String day) {

        dailyService.createStatisticsByDay(day);
        return R.ok().message("统计信息生成成功");
    }

    @ApiOperation("获取某天的统计信息")
    @GetMapping("/get/{day}")
    public R getStatisticsByDay(@ApiParam("统计日期") @PathVariable("day") String day) {

        QueryWrapper<Daily> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("date_calculated", day);
        Daily daily = dailyService.getOne(queryWrapper);
        return R.ok().data("daily", daily);
    }

    @ApiOperation("获取某个时间段内统计信息")
    @GetMapping("/between/{begin}/{end}")
    public R getStatisticsByDate(@ApiParam("统计日期") @PathVariable("begin") String begin,
                                 @ApiParam("统计日期") @PathVariable("end") String end) {

        if (StringUtils.isBlank(begin)) {
            return R.error().message("请输入正确的日期时间");
        }

        List<Daily> list = dailyService.getStatisticsByDate(begin, end);

        return R.ok().data("items", list);
    }

    @ApiOperation("导出某个时间范围内的信息")
    @GetMapping("/export/{begin}/{end}")
    public void export(@ApiParam("统计日期") @PathVariable("begin") String begin,
                        @ApiParam("统计日期") @PathVariable("end") String end,
                        HttpServletResponse response) {

        if (StringUtils.isBlank(begin) || StringUtils.isBlank(end)) {
            throw new RException(ResultCodeEnum.PARAM_ERROR);
        }

        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = null;
        try {
            fileName = URLEncoder.encode("课程分类", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

            // 设置样式
            // 头的策略
            WriteCellStyle headWriteCellStyle = new WriteCellStyle();
            // 背景设置为
            headWriteCellStyle.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());
            WriteFont headWriteFont = new WriteFont();
            headWriteFont.setFontHeightInPoints((short)20);
            headWriteCellStyle.setWriteFont(headWriteFont);


            // 内容的策略
            WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
            // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND
            // 不然无法显示背景颜色.头默认了 FillPatternType所以可以不指定
            contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
            // 背景白色
            contentWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE1.getIndex());
            WriteFont contentWriteFont = new WriteFont();
            // 字体大小
            contentWriteFont.setFontHeightInPoints((short)14);
            contentWriteCellStyle.setWriteFont(contentWriteFont);

            // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
            HorizontalCellStyleStrategy horizontalCellStyleStrategy =
                    new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);


            EasyExcel.write(response.getOutputStream(), DownLoadDaily.class)
                    .registerWriteHandler(horizontalCellStyleStrategy)
                    .sheet("sheet1").doWrite(dailyService.exportStatisticsByDate(begin, end));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
