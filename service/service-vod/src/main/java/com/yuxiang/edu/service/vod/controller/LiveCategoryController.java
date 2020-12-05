package com.yuxiang.edu.service.vod.controller;


import com.yuxiang.edu.common.result.R;
import com.yuxiang.edu.service.vod.entity.LiveCategory;
import com.yuxiang.edu.service.vod.entity.vo.LiveCategoryVO;
import com.yuxiang.edu.service.vod.service.LiveCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 直播分类表 前端控制器
 * </p>
 *
 * @author yuxiang
 * @since 2020-11-18
 */
@Api(description = "直播分类控制类")
@RestController
@RequestMapping("/api/vod/live-category")
public class LiveCategoryController {


    @Autowired
    private LiveCategoryService liveCategoryService;

    @ApiOperation("获取全部分类")
    @GetMapping("/all")
    public R getAll() {
        List<LiveCategory> categorys = liveCategoryService.getAll();

        return R.ok().data("liveCategory", categorys);
    }

    @ApiOperation("获取全部分类【嵌套返回】")
    @GetMapping("/nested-list")
    public R nestedList() {
        List<LiveCategoryVO> categorys = liveCategoryService.getNestedList();

        return R.ok().data("items", categorys);
    }


}

