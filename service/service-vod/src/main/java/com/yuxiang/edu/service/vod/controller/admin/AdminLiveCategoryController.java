package com.yuxiang.edu.service.vod.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuxiang.edu.common.result.R;
import com.yuxiang.edu.common.result.ResultCodeEnum;
import com.yuxiang.edu.common.util.ExceptionUtils;
import com.yuxiang.edu.service.base.exception.RException;
import com.yuxiang.edu.service.vod.entity.LiveCategory;
import com.yuxiang.edu.service.vod.service.LiveCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author: yuxiang
 * @Date: 2020/11/18 19:40
 */
@CrossOrigin
@Api(description = "直播分类控制类")
@RestController
@RequestMapping("/admin/vod/live-category")
@Slf4j
public class AdminLiveCategoryController {

    @Autowired
    private LiveCategoryService liveCategoryService;

    @ApiOperation("添加分类信息")
    @PostMapping("/add")
    public R add(@ApiParam("分类信息") @RequestBody LiveCategory liveCategory) {

        liveCategoryService.save(liveCategory);
        return R.ok();
    }

    @ApiOperation("更新分类信息")
    @PutMapping("/update")
    public R update(@ApiParam("分类信息") @RequestBody LiveCategory liveCategory) {
        QueryWrapper<LiveCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", liveCategory.getId());
        liveCategoryService.update(liveCategory, queryWrapper);
        return R.ok();
    }

    @ApiOperation("删除分类信息")
    @DeleteMapping("/delete/{id}")
    public R delete(@ApiParam("分类ID") @PathVariable("id") String id) {

        liveCategoryService.removeById(id);
        return R.ok();
    }

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

}
