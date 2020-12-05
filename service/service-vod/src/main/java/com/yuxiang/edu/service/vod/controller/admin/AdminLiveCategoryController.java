package com.yuxiang.edu.service.vod.controller.admin;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuxiang.edu.common.result.R;
import com.yuxiang.edu.common.result.ResultCodeEnum;
import com.yuxiang.edu.common.util.ExceptionUtils;
import com.yuxiang.edu.service.base.exception.RException;
import com.yuxiang.edu.service.vod.entity.LiveCategory;
import com.yuxiang.edu.service.vod.entity.excel.DownLoadLiveCategorytData;
import com.yuxiang.edu.service.vod.service.LiveCategoryService;
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
 * @Date: 2020/11/18 19:40
 */
@Api(description = "直播分类控制类")
@Controller
@RequestMapping("/admin/vod/live-category")
@Slf4j
public class AdminLiveCategoryController {

    @Autowired
    private LiveCategoryService liveCategoryService;

    @ResponseBody
    @ApiOperation("添加分类信息")
    @PostMapping("/add")
    public R add(@ApiParam("分类信息") @RequestBody LiveCategory liveCategory) {

        liveCategoryService.save(liveCategory);
        return R.ok();
    }

    @ResponseBody
    @ApiOperation("更新分类信息")
    @PutMapping("/update")
    public R update(@ApiParam("分类信息") @RequestBody LiveCategory liveCategory) {
        QueryWrapper<LiveCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", liveCategory.getId());
        liveCategoryService.update(liveCategory, queryWrapper);
        return R.ok();
    }

    @ResponseBody
    @ApiOperation("删除分类信息")
    @DeleteMapping("/delete/{id}")
    public R delete(@ApiParam("分类ID") @PathVariable("id") String id) {

        liveCategoryService.removeById(id);
        return R.ok();
    }

    @ResponseBody
    @ApiOperation("导入Excel表格，录入直播分类信息")
    @PostMapping("/import")
    public R importCategory(@ApiParam(value = "Excel文件流", required = true) @RequestParam("file") MultipartFile file) {

        try {
            liveCategoryService.batchImport(file.getInputStream());
        } catch (IOException e) {
            log.error(ExceptionUtils.getMessage(e));
            throw new RException(ResultCodeEnum.EXCEL_DATA_IMPORT_ERROR);
        }
        return R.ok().message("批量导入成功");
    }

    @ApiOperation("导出Excel表格")
    @GetMapping("/export")
    public void exportLiveCategory(HttpServletResponse response) {

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
            contentWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE1.index);
            WriteFont contentWriteFont = new WriteFont();
            // 字体大小
            contentWriteFont.setFontHeightInPoints((short)14);
            contentWriteCellStyle.setWriteFont(contentWriteFont);

            // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
            HorizontalCellStyleStrategy horizontalCellStyleStrategy =
                    new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);


            EasyExcel.write(response.getOutputStream(), DownLoadLiveCategorytData.class)
                    .registerWriteHandler(horizontalCellStyleStrategy)
                    .sheet("sheet1").doWrite(data());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<DownLoadLiveCategorytData> data() {
        List<DownLoadLiveCategorytData> list = liveCategoryService.getExportData();
        return list;
    }


}
