package com.yuxiang.edu.service.core.controller.admin;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.yuxiang.edu.common.result.R;
import com.yuxiang.edu.common.result.ResultCodeEnum;
import com.yuxiang.edu.common.util.ExceptionUtils;
import com.yuxiang.edu.service.base.exception.RException;
import com.yuxiang.edu.service.core.entity.excel.DownLoadSubjectData;
import com.yuxiang.edu.service.core.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @Author: yuxiang
 * @Date: 2020/11/22 11:02
 */

@Slf4j
@Api(description = "课程分类控制器")
@Controller
@RequestMapping("/admin/core/subject")
public class AdminSubjectController {

    @Autowired
    private SubjectService subjectService;

    @ResponseBody
    @ApiOperation("Excel批量导入课程分类")
    @PostMapping("/import")
    public R batchImport(@ApiParam(value = "Excel文件流", required = true) @RequestParam("file") MultipartFile file) {
        try {
            subjectService.batchImport(file.getInputStream());
        } catch (IOException e) {
            log.error(ExceptionUtils.getMessage(e));
            throw new RException(ResultCodeEnum.EXCEL_DATA_IMPORT_ERROR);
        }
        return R.ok().message("批量导入成功");
    }


    /**
     * 文件下载（失败了会返回一个有部分数据的Excel）
     * @param response
     * @return
     */
    @ApiOperation("课程分类导出到Excel")
    @GetMapping("/export")
    public void export(HttpServletResponse response) {

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


            EasyExcel.write(response.getOutputStream(), DownLoadSubjectData.class)
                    .registerWriteHandler(horizontalCellStyleStrategy)
                    .sheet("sheet1").doWrite(data());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<DownLoadSubjectData> data() {
        List<DownLoadSubjectData> list = subjectService.getExportData();
        return list;
    }

}
