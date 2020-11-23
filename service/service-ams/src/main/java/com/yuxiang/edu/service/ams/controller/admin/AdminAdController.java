package com.yuxiang.edu.service.ams.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yuxiang.edu.common.result.R;
import com.yuxiang.edu.service.ams.entity.Ad;
import com.yuxiang.edu.service.ams.service.AdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: yuxiang
 * @Date: 2020/11/22 13:44
 */
@CrossOrigin
@Api(description = "广告推荐控制器")
@RestController
@RequestMapping("/admin/ams/ad")
@Slf4j
public class AdminAdController {

    @Autowired
    private AdService adService;

    @ApiOperation(value = "根据ID删除推荐")
    @DeleteMapping("/remove/{id}")
    public R removeById(@ApiParam(value = "推荐ID", required = true) @PathVariable String id) {

        //删除图片
        adService.removeAdImageById(id);

        //删除推荐
        boolean result = adService.removeById(id);
        if (result) {
            return R.ok().message("删除成功");
        } else {
            return R.error().message("数据不存在");
        }
    }

    @ApiOperation("推荐分页")
    @GetMapping("/list/{page}/{limit}")
    public R listPage(@ApiParam(value = "当前页码", required = true) @PathVariable Long page,
                      @ApiParam(value = "每页记录数", required = true) @PathVariable Long limit) {

        IPage<Ad> pageModel = adService.selectPage(page, limit);
        List<Ad> records = pageModel.getRecords();
        long total = pageModel.getTotal();
        return R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation("新增推荐")
    @PostMapping("/save")
    public R save(@ApiParam(value = "推荐对象", required = true) @RequestBody Ad ad) {

        boolean result = adService.save(ad);
        if (result) {
            return R.ok().message("保存成功");
        } else {
            return R.error().message("保存失败");
        }
    }

    @ApiOperation("更新推荐")
    @PutMapping("/update")
    public R updateById(@ApiParam(value = "讲师推荐", required = true) @RequestBody Ad ad) {
        boolean result = adService.updateById(ad);
        if (result) {
            return R.ok().message("修改成功");
        } else {
            return R.error().message("数据不存在");
        }
    }

    @ApiOperation("根据id获取推荐信息")
    @GetMapping("/get/{id}")
    public R getById(@ApiParam(value = "推荐ID", required = true) @PathVariable String id) {
        Ad ad = adService.getById(id);
        if (ad != null) {
            return R.ok().data("item", ad);
        } else {
            return R.error().message("数据不存在");
        }
    }
}